package io.github.leassets.internal.service;

import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.domain.FixedAssetDepreciation;
import io.github.leassets.domain.FixedAssetNetBookValue;
import io.github.leassets.internal.model.FixedAssetAcquisitionEntityRepository;
import io.github.leassets.internal.model.FixedAssetDepreciationEntityRepository;
import io.github.leassets.internal.model.FixedAssetNetBookValueEntityRepository;
import io.github.leassets.internal.model.framework.DefaultBatchService;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import io.github.leassets.service.dto.FixedAssetDepreciationDTO;
import io.github.leassets.service.dto.FixedAssetNetBookValueDTO;
import io.github.leassets.service.mapper.FixedAssetAcquisitionMapper;
import io.github.leassets.service.mapper.FixedAssetDepreciationMapper;
import io.github.leassets.service.mapper.FixedAssetNetBookValueMapper;
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
    public BatchService<FixedAssetAcquisitionDTO> fixedAssetAcquisitionBatchService() {
        return new DefaultBatchService<FixedAssetAcquisitionDTO, FixedAssetAcquisition>(
            fixedAssetAcquisitionMapper,
            fixedAssetAcquisitionRepository
        );
    }

    @Bean("fixedAssetDepreciationBatchService")
    public BatchService<FixedAssetDepreciationDTO> fixedAssetDepreciationBatchService() {
        return new DefaultBatchService<FixedAssetDepreciationDTO, FixedAssetDepreciation>(
            fixedAssetDepreciationMapper,
            fixedAssetDepreciationRepository
        );
    }

    @Bean("fixedAssetNetBookValueBatchService")
    public BatchService<FixedAssetNetBookValueDTO> fixedAssetNetBookValueBatchService() {
        return new DefaultBatchService<FixedAssetNetBookValueDTO, FixedAssetNetBookValue>(
            fixedAssetNetBookValueMapper,
            fixedAssetNetBookValueRepository
        );
    }
}
