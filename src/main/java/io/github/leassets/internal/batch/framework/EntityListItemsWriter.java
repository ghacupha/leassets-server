package io.github.leassets.internal.batch.framework;

import io.github.leassets.internal.service.BatchService;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Performs persistence of the newly created entities
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
