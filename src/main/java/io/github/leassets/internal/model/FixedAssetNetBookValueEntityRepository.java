package io.github.leassets.internal.model;

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

import io.github.leassets.domain.FixedAssetNetBookValue;
import io.github.leassets.internal.model.framework.EntityRepository;
import io.github.leassets.repository.FixedAssetNetBookValueRepository;
import io.github.leassets.repository.search.FixedAssetNetBookValueSearchRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A transactional batch persistence service for the fixed-asset-net-book-value entity
 */
@Transactional
@Service
public class FixedAssetNetBookValueEntityRepository implements EntityRepository<FixedAssetNetBookValue> {

    private final FixedAssetNetBookValueSearchRepository fixedAssetNetBookValueSearchRepository;

    private final FixedAssetNetBookValueRepository fixedAssetNetBookValueRepository;

    public FixedAssetNetBookValueEntityRepository(
        FixedAssetNetBookValueSearchRepository fixedAssetNetBookValueSearchRepository,
        FixedAssetNetBookValueRepository fixedAssetNetBookValueRepository
    ) {
        this.fixedAssetNetBookValueSearchRepository = fixedAssetNetBookValueSearchRepository;
        this.fixedAssetNetBookValueRepository = fixedAssetNetBookValueRepository;
    }

    @Override
    public void indexAll(List<FixedAssetNetBookValue> entities) {
        fixedAssetNetBookValueSearchRepository.saveAll(entities);
    }

    @Override
    public List<FixedAssetNetBookValue> saveAll(List<FixedAssetNetBookValue> entities) {
        return fixedAssetNetBookValueRepository.saveAll(entities);
    }
}
