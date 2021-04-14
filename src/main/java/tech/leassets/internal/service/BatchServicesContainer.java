package tech.leassets.internal.service;

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

import tech.leassets.domain.FixedAssetAcquisition;
import tech.leassets.domain.FixedAssetDepreciation;
import tech.leassets.domain.FixedAssetNetBookValue;
import tech.leassets.internal.model.FixedAssetAcquisitionEntityRepository;
import tech.leassets.internal.model.FixedAssetDepreciationEntityRepository;
import tech.leassets.internal.model.FixedAssetNetBookValueEntityRepository;
import tech.leassets.internal.model.framework.DefaultBatchService;
import tech.leassets.service.dto.FixedAssetAcquisitionDTO;
import tech.leassets.service.dto.FixedAssetDepreciationDTO;
import tech.leassets.service.dto.FixedAssetNetBookValueDTO;
import tech.leassets.service.mapper.FixedAssetAcquisitionMapper;
import tech.leassets.service.mapper.FixedAssetDepreciationMapper;
import tech.leassets.service.mapper.FixedAssetNetBookValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Configuration
public class BatchServicesContainer {

    @Autowired
    private FixedAssetAcquisitionMapper fixedAssetAcquisitionMapper;

    @Autowired
    private FixedAssetAcquisitionEntityRepository fixedAssetAcquisitionRepository;

    @Autowired
    private FixedAssetDepreciationMapper fixedAssetDepreciationMapper;

    @Autowired
    private FixedAssetDepreciationEntityRepository fixedAssetDepreciationRepository;

    @Autowired
    private FixedAssetNetBookValueMapper fixedAssetNetBookValueMapper;

    @Autowired
    private FixedAssetNetBookValueEntityRepository fixedAssetNetBookValueRepository;

    @Bean("fixedAssetAcquisitionBatchService")
    public tech.leassets.internal.service.BatchService<FixedAssetAcquisitionDTO> fixedAssetAcquisitionBatchService() {
        return new DefaultBatchService<FixedAssetAcquisitionDTO, FixedAssetAcquisition>(
            fixedAssetAcquisitionMapper,
            fixedAssetAcquisitionRepository
        );
    }

    @Bean("fixedAssetDepreciationBatchService")
    public tech.leassets.internal.service.BatchService<FixedAssetDepreciationDTO> fixedAssetDepreciationBatchService() {
        return new DefaultBatchService<FixedAssetDepreciationDTO, FixedAssetDepreciation>(
            fixedAssetDepreciationMapper,
            fixedAssetDepreciationRepository
        );
    }

    @Bean("fixedAssetNetBookValueBatchService")
    public tech.leassets.internal.service.BatchService<FixedAssetNetBookValueDTO> fixedAssetNetBookValueBatchService() {
        return new DefaultBatchService<FixedAssetNetBookValueDTO, FixedAssetNetBookValue>(
            fixedAssetNetBookValueMapper,
            fixedAssetNetBookValueRepository
        );
    }
}
