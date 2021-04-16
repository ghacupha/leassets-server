package io.github.leassets.internal.service;

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

import io.github.jhipster.service.filter.LongFilter;
import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.internal.framework.batch.DeletionService;
import io.github.leassets.repository.FixedAssetAcquisitionRepository;
import io.github.leassets.repository.search.FixedAssetAcquisitionSearchRepository;
import io.github.leassets.service.FixedAssetAcquisitionQueryService;
import io.github.leassets.service.dto.FixedAssetAcquisitionCriteria;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import io.github.leassets.service.mapper.EntityMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Transactional
@Service
public class FixedAssetAcquisitionDeletionService implements DeletionService<FixedAssetAcquisition> {


    private final FixedAssetAcquisitionRepository fixedAssetAcquisitionRepository;
    private final FixedAssetAcquisitionSearchRepository fixedAssetAcquisitionSearchRepository;
    private final FixedAssetAcquisitionQueryService fixedAssetAcquisitionQueryService;
    private final EntityMapper<FixedAssetAcquisitionDTO, FixedAssetAcquisition> fixedAssetAcquisitionMapper;

    public FixedAssetAcquisitionDeletionService(
        final FixedAssetAcquisitionRepository fixedAssetAcquisitionRepository,
        final FixedAssetAcquisitionSearchRepository fixedAssetAcquisitionSearchRepository,
        final FixedAssetAcquisitionQueryService fixedAssetAcquisitionQueryService,
        final EntityMapper<FixedAssetAcquisitionDTO, FixedAssetAcquisition> fixedAssetAcquisitionMapper
    ) {
        this.fixedAssetAcquisitionRepository = fixedAssetAcquisitionRepository;
        this.fixedAssetAcquisitionSearchRepository = fixedAssetAcquisitionSearchRepository;
        this.fixedAssetAcquisitionQueryService = fixedAssetAcquisitionQueryService;
        this.fixedAssetAcquisitionMapper = fixedAssetAcquisitionMapper;
    }

    /**
     * Implements batch deletion for entities with the ids in the list provided
     *
     * @param list of ids of objects to delete
     * @return List of deleted and detached objects
     */
    @Override
    @Async
    public List<FixedAssetAcquisition> delete(final List<Long> list) {
        List<FixedAssetAcquisition> deletedAccounts = new CopyOnWriteArrayList<>();

        Optional<List<FixedAssetAcquisition>> deletable = Optional.of(new CopyOnWriteArrayList<>());

        List<FixedAssetAcquisitionCriteria> criteriaList = new CopyOnWriteArrayList<>();

        list.forEach(
            id -> {
                LongFilter idFilter = new LongFilter();
                idFilter.setEquals(id);
                FixedAssetAcquisitionCriteria criteria = new FixedAssetAcquisitionCriteria();
                criteria.setId(idFilter);
                criteriaList.add(criteria);
            }
        );

        criteriaList.forEach(
            criteria ->
                deletable.ifPresent(
                    toDelete ->
                        toDelete.addAll(
                            fixedAssetAcquisitionQueryService
                                .findByCriteria(criteria)
                                .stream()
                                .map(fixedAssetAcquisitionMapper::toEntity)
                                .collect(Collectors.toList())
                        )
                )
        );

        deletable.ifPresent(deletedAccounts::addAll);

        deletable.ifPresent(fixedAssetAcquisitionRepository::deleteAll);

        deletable.ifPresent(
            deletableItems ->
                fixedAssetAcquisitionSearchRepository.deleteAll(
                    // use deletable
                    deletableItems
                        .stream()
                        .filter(ac -> fixedAssetAcquisitionSearchRepository.existsById(ac.getId()))
                        .collect(Collectors.toList())
                )
        );

        return deletedAccounts;
    }
}
