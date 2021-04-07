package io.github.leassets.internal.batch.framework;

import static org.slf4j.LoggerFactory.getLogger;

import io.github.leassets.internal.service.DeletionService;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.batch.item.ItemProcessor;

/**
 * This abstraction for deletion of entities from the DB in batch
 * @param <Entity>
 */
public class EntityDeletionProcessor<Entity> implements ItemProcessor<List<Long>, List<Entity>> {

    private static final Logger log = getLogger(EntityDeletionProcessor.class);
    private final DeletionService<Entity> deletionService;

    public EntityDeletionProcessor(final DeletionService<Entity> deletionService) {
        this.deletionService = deletionService;
    }

    @Override
    public List<Entity> process(final List<Long> list) throws Exception {
        log.debug("Deletion processor iterating over {} deletion item ids", list.size());
        return deletionService.delete(list);
    }
}
