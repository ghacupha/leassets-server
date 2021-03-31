package io.github.leassets.internal.batch.framework;

import io.github.leassets.internal.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Most of the time the only job this processor is doing is mapping excel-view-models to respective
 * data-transfer-objects.
 * The developer is expected to use the mapping provided to map the EVM to DTO and also to apply the file-upload
 * tag from the batch-process
 */
public abstract class EntityItemsProcessor<EVM, DTO> implements ItemProcessor<List<EVM>, List<DTO>> {

    private final static Logger log = LoggerFactory.getLogger(EntityItemsProcessor.class);

    protected final Mapping<EVM, DTO> mapping;
    protected final String jobUploadToken;

    public EntityItemsProcessor(Mapping<EVM, DTO> mapping, String jobUploadToken) {
        this.mapping = mapping;
        this.jobUploadToken = jobUploadToken;
    }

    @Override
    public List<DTO> process(final List<EVM> evms) throws Exception {
        log.info("Sending {} for processing...", evms.size());
        return getDtoStream(evms);
    }

    protected abstract List<DTO> getDtoStream(List<EVM> evms);
}
