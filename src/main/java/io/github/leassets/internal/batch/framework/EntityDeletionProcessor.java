package io.github.leassets.internal.batch.framework;

import io.github.leassets.internal.service.DeletionService;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

/**
 * This abstraction for deletion of entities from the DB in batch
 * @param <Entity>
 */
public class EntityDeletionProcessor<Entity> implements ItemProcessor<List<Long>, List<Entity>> {

    private final DeletionService<Entity> deletionService;

    public EntityDeletionProcessor(final DeletionService<Entity> deletionService) {
        this.deletionService = deletionService;
    }

    @Override
    public List<Entity> process(final List<Long> list) throws Exception {
        return deletionService.delete(list);
    }
}
