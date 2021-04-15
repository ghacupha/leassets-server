package io.github.leassets.internal.service;

import io.github.leassets.internal.framework.BatchService;
import io.github.leassets.repository.FixedAssetAcquisitionRepository;
import io.github.leassets.repository.search.FixedAssetAcquisitionSearchRepository;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import io.github.leassets.service.mapper.FixedAssetAcquisitionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("fixedAssetAcquisitionBatchService")
public class FixedAssetAcquisitionBatchService implements BatchService<FixedAssetAcquisitionDTO> {

    private final FixedAssetAcquisitionMapper mapper;
    private final FixedAssetAcquisitionRepository repository;
    private final FixedAssetAcquisitionSearchRepository searchRepository;

    public FixedAssetAcquisitionBatchService(FixedAssetAcquisitionMapper mapper, FixedAssetAcquisitionRepository repository, FixedAssetAcquisitionSearchRepository searchRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.searchRepository = searchRepository;
    }

    @Override
    public List<FixedAssetAcquisitionDTO> save(List<FixedAssetAcquisitionDTO> entities) {
        return mapper.toDto(repository.saveAll(mapper.toEntity(entities)));
    }

    @Override
    public void index(List<FixedAssetAcquisitionDTO> entities) {

        searchRepository.saveAll(mapper.toEntity(entities));
    }
}
