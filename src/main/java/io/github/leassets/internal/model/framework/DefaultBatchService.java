package io.github.leassets.internal.model.framework;

/*-
 * Leassets Server - Leases and assets management platform
 * Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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

    public DefaultBatchService(EntityMapper<T, D> entityMapper, EntityRepository<D> repository) {
        this.entityMapper = entityMapper;
        this.repository = repository;
    }

    @Override
    public List<T> save(List<T> entities) {
        return entityMapper.toDto(repository.saveAll(entityMapper.toEntity(entities)));
    }

    @Override
    public void index(List<T> entities) {
        repository.indexAll(entityMapper.toEntity(entities));
    }
}
