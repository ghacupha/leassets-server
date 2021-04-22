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

import com.google.common.util.concurrent.Futures;
import io.github.jhipster.service.filter.LongFilter;
import io.github.leassets.domain.FixedAssetDepreciation;
import io.github.leassets.internal.framework.batch.DeletionService;
import io.github.leassets.repository.FixedAssetDepreciationRepository;
import io.github.leassets.repository.search.FixedAssetDepreciationSearchRepository;
import io.github.leassets.service.FixedAssetDepreciationQueryService;
import io.github.leassets.service.dto.FixedAssetDepreciationCriteria;
import io.github.leassets.service.dto.FixedAssetDepreciationDTO;
import io.github.leassets.service.mapper.EntityMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Transactional
@Service
public class FixedAssetDepreciationDeletionService implements DeletionService<FixedAssetDepreciation> {
    private final FixedAssetDepreciationRepository fixedAssetDepreciationRepository;
    private final FixedAssetDepreciationSearchRepository fixedAssetDepreciationSearchRepository;
    private final FixedAssetDepreciationQueryService fixedAssetDepreciationQueryService;
    private final EntityMapper<FixedAssetDepreciationDTO, FixedAssetDepreciation> fixedAssetDepreciationMapper;

    public FixedAssetDepreciationDeletionService(FixedAssetDepreciationRepository fixedAssetDepreciationRepository, FixedAssetDepreciationSearchRepository fixedAssetDepreciationSearchRepository, FixedAssetDepreciationQueryService fixedAssetDepreciationQueryService, EntityMapper<FixedAssetDepreciationDTO, FixedAssetDepreciation> fixedAssetDepreciationMapper) {
        this.fixedAssetDepreciationRepository = fixedAssetDepreciationRepository;
        this.fixedAssetDepreciationSearchRepository = fixedAssetDepreciationSearchRepository;
        this.fixedAssetDepreciationQueryService = fixedAssetDepreciationQueryService;
        this.fixedAssetDepreciationMapper = fixedAssetDepreciationMapper;
    }

    /**
     * Implements batch deletion for entities with the ids in the list provided
     *
     * @param list of ids of objects to delete
     * @return List of deleted and detached objects
     */
    @Override
    @Async
    public Future<List<FixedAssetDepreciation>> delete(final List<Long> list) {
        List<FixedAssetDepreciation> deletedAccounts = new CopyOnWriteArrayList<>();

        Optional<List<FixedAssetDepreciation>> deletable = Optional.of(new CopyOnWriteArrayList<>());

        List<FixedAssetDepreciationCriteria> criteriaList = new CopyOnWriteArrayList<>();

        list.forEach(
            id -> {
                LongFilter idFilter = new LongFilter();
                idFilter.setEquals(id);
                FixedAssetDepreciationCriteria criteria = new FixedAssetDepreciationCriteria();
                criteria.setId(idFilter);
                criteriaList.add(criteria);
            }
        );

        mapCriteriaList(deletable, criteriaList, fixedAssetDepreciationQueryService);

        deletable.ifPresent(deletedAccounts::addAll);

        deletable.ifPresent(fixedAssetDepreciationRepository::deleteAll);

        deletable.ifPresent(
            deletableItems ->
                fixedAssetDepreciationSearchRepository.deleteAll(
                    // use deletable
                    deletableItems
                        .stream()
                        .filter(ac -> fixedAssetDepreciationSearchRepository.existsById(ac.getId()))
                        .collect(Collectors.toList())
                )
        );

        return Futures.immediateFuture(deletedAccounts);
    }

    public void mapCriteriaList(Optional<List<FixedAssetDepreciation>> deletable, List<FixedAssetDepreciationCriteria> criteriaList, FixedAssetDepreciationQueryService fixedAssetDepreciationQueryService) {
        criteriaList.forEach(
            criteria ->
                deletable.ifPresent(
                    toDelete ->
                        toDelete.addAll(
                            fixedAssetDepreciationQueryService
                                .findByCriteria(criteria)
                                .stream()
                                .map(fixedAssetDepreciationMapper::toEntity)
                                .collect(Collectors.toList())
                        )
                )
        );
    }
}
