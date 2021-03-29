package io.github.leassets.internal.model;

import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.internal.service.BatchService;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import io.github.leassets.service.mapper.FixedAssetAcquisitionMapper;
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
    private FixedAssetAcquisitionEntitySearchRepository fixedAssetAcquisitionSearchRepository;

    @Bean("fixedAssetAcquisitionBatchService")
    public BatchService<FixedAssetAcquisitionDTO> fixedAssetAcquisitionBatchService() {

        return new DefaultBatchService<FixedAssetAcquisitionDTO, FixedAssetAcquisition>(
            fixedAssetAcquisitionMapper,
            fixedAssetAcquisitionRepository,
            fixedAssetAcquisitionSearchRepository
        );
    }
}
