package io.github.leassets.internal.batch.framework;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import org.slf4j.Logger;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

/**
 * This read-step represents the common structure of how collections are passed from the reader to the writer
 * in a read file step
 *
 * @param <EVM>
 * @param <DTO>
 */
public class ReadFileStep<EVM, DTO> implements Step {

    private static final Logger log = getLogger(ReadFileStep.class);

    private final Step theStep;
    private final String stepName;

    public ReadFileStep(
        final String stepName,
        final ItemReader<List<EVM>> itemReader,
        final ItemProcessor<List<EVM>, List<DTO>> itemProcessor,
        final ItemWriter<List<DTO>> itemWriter,
        final StepBuilderFactory stepBuilderFactory
    ) {
        theStep =
            stepBuilderFactory
                .get(stepName)
                .<List<EVM>, List<DTO>>chunk(2)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
        this.stepName = stepName;
    }

    @Override
    public String getName() {
        log.debug("Creating read-file-step of id : {}", stepName);
        return this.theStep.getName();
    }

    @Override
    public boolean isAllowStartIfComplete() {
        return this.theStep.isAllowStartIfComplete();
    }

    @Override
    public int getStartLimit() {
        return this.theStep.getStartLimit();
    }

    @Override
    public void execute(StepExecution stepExecution) throws JobInterruptedException {
        log.debug("Commencing read-file batch step execution for batch step id {}", stepName);
        this.theStep.execute(stepExecution);
    }
}
