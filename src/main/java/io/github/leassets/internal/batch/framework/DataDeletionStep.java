package io.github.leassets.internal.batch.framework;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

/**
 * Abstract data deletion step. It uses the step-builder-factory to create a step whose
 * configuration generates a {@link List} of Long items which mark the indices of instances
 * of Entity E to be deleted from the persistence layer.
 *
 * @param <E> Entity whose table is to be deleted
 */
public class DataDeletionStep<E> implements Step {

    private static final Logger log = LoggerFactory.getLogger(DataDeletionStep.class);

    private final Step step;
    private final String stepName;

    public DataDeletionStep(
        final StepBuilderFactory stepBuilderFactory,
        final String stepName,
        final ItemReader<List<Long>> longListReader,
        final ItemProcessor<List<Long>, List<E>> longListProcessor,
        final ItemWriter<? super List<E>> entityListWriter
    ) {
        step =
            stepBuilderFactory
                .get(stepName)
                .<List<Long>, List<E>>chunk(2)
                .reader(longListReader)
                .processor(longListProcessor)
                .writer(entityListWriter)
                .build();
        this.stepName = stepName;
    }

    @Override
    public String getName() {
        log.debug("Creating batch step id : {} ...", stepName);
        return step.getName();
    }

    @Override
    public boolean isAllowStartIfComplete() {
        return step.isAllowStartIfComplete();
    }

    @Override
    public int getStartLimit() {
        return step.getStartLimit();
    }

    @Override
    public void execute(StepExecution stepExecution) throws JobInterruptedException {
        log.debug("Commencing execution of batch step id : {}", stepName);
        step.execute(stepExecution);
    }
}
