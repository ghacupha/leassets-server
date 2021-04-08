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

import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.internal.model.framework.EntityRepository;
import io.github.leassets.repository.FixedAssetAcquisitionRepository;
import io.github.leassets.repository.search.FixedAssetAcquisitionSearchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Transactional batch persistence serviece for fixed-asset-acquisition
 */
@Service
@Transactional
public class FixedAssetAcquisitionEntityRepository implements EntityRepository<FixedAssetAcquisition> {

    private final FixedAssetAcquisitionRepository fixedAssetAcquisitionRepository;

    private final FixedAssetAcquisitionSearchRepository fixedAssetAcquisitionSearchRepository;

    public FixedAssetAcquisitionEntityRepository(
        @Qualifier("fixedAssetAcquisitionRepository") FixedAssetAcquisitionRepository fixedAssetAcquisitionRepository,
        @Qualifier("fixedAssetAcquisitionSearchRepository") FixedAssetAcquisitionSearchRepository fixedAssetAcquisitionSearchRepository
    ) {
        this.fixedAssetAcquisitionRepository = fixedAssetAcquisitionRepository;
        this.fixedAssetAcquisitionSearchRepository = fixedAssetAcquisitionSearchRepository;
    }

    @Override
    public void indexAll(List<FixedAssetAcquisition> entities) {
        fixedAssetAcquisitionSearchRepository.saveAll(entities);
    }

    @Override
    public List<FixedAssetAcquisition> saveAll(List<FixedAssetAcquisition> entities) {
        return fixedAssetAcquisitionRepository.saveAll(entities);
    }
}
