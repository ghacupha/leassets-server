package io.github.leassets.internal.model.framework;

import io.github.leassets.internal.service.BatchService;
import io.github.leassets.service.mapper.EntityMapper;

import java.util.List;

/**
 * Default but abstract implementation of the Batch service using domain interfaces. The same has helped
 * reduce copying between various implementations though the developer is required to provide implementation
 * of all the below interfaces in a container
 *
 * @param <T> DTO for the entity
 * @param <D> Entity object
 */
public class DefaultBatchService<T, D> implements BatchService<T> {

    // Mapper
    private final EntityMapper<T, D> entityMapper;

    // Domain repository
    private final EntityRepository<D> repository;

    // Search repository
    private final EntitySearchRepository<D> searchRepository;

    public DefaultBatchService(EntityMapper<T, D> entityMapper, EntityRepository<D> repository, EntitySearchRepository<D> searchRepository) {
        this.entityMapper = entityMapper;
        this.repository = repository;
        this.searchRepository = searchRepository;
    }

    @Override
    public List<T> save(List<T> entities) {
        return entityMapper.toDto(repository.saveAll(entityMapper.toEntity(entities)));
    }

    @Override
    public void index(List<T> entities) {
        searchRepository.saveAll(entityMapper.toEntity(entities));
    }
}
