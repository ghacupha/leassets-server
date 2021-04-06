package io.github.leassets.internal.batch.framework;

import io.github.leassets.internal.service.BatchService;
import java.util.List;
import org.springframework.batch.item.ItemWriter;

/**
 * Performs persistence of the newly created entities through the batch-service
 *
 * @param <DTO>
 */
public class EntityListItemsWriter<DTO> implements ItemWriter<List<DTO>> {

    private final BatchService<DTO> batchService;

    public EntityListItemsWriter(BatchService<DTO> batchService) {
        this.batchService = batchService;
    }

    @Override
    public void write(List<? extends List<DTO>> items) throws Exception {
        items.stream().peek(batchService::save).forEach(batchService::index);
    }
}
