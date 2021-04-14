package tech.leassets.internal.model;

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

import tech.leassets.domain.FixedAssetDepreciation;
import tech.leassets.internal.model.framework.EntityRepository;
import tech.leassets.repository.FixedAssetDepreciationRepository;
import tech.leassets.repository.search.FixedAssetDepreciationSearchRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A transactional batch persistence service for the fixed-asset-depreciation entity
 */
@Service
@Transactional
public class FixedAssetDepreciationEntityRepository implements EntityRepository<FixedAssetDepreciation> {

    private final FixedAssetDepreciationSearchRepository fixedAssetDepreciationSearchRepository;

    private final FixedAssetDepreciationRepository fixedAssetDepreciationRepository;

    public FixedAssetDepreciationEntityRepository(
        @Qualifier("fixedAssetDepreciationSearchRepository") FixedAssetDepreciationSearchRepository fixedAssetDepreciationSearchRepository,
        @Qualifier("fixedAssetDepreciationRepository") FixedAssetDepreciationRepository fixedAssetDepreciationRepository
    ) {
        this.fixedAssetDepreciationSearchRepository = fixedAssetDepreciationSearchRepository;
        this.fixedAssetDepreciationRepository = fixedAssetDepreciationRepository;
    }

    @Override
    public void indexAll(List<FixedAssetDepreciation> entities) {
        fixedAssetDepreciationSearchRepository.saveAll(entities);
    }

    @Override
    public List<FixedAssetDepreciation> saveAll(List<FixedAssetDepreciation> entities) {
        return fixedAssetDepreciationRepository.saveAll(entities);
    }
}
