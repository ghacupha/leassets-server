package io.github.leassets.internal.service;

import io.github.leassets.internal.framework.BatchService;
import io.github.leassets.repository.FixedAssetDepreciationRepository;
import io.github.leassets.repository.search.FixedAssetDepreciationSearchRepository;
import io.github.leassets.service.dto.FixedAssetDepreciationDTO;
import io.github.leassets.service.mapper.FixedAssetDepreciationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("fixedAssetDepreciationBatchService")
public class FixedAssetDepreciationBatchService implements BatchService<FixedAssetDepreciationDTO> {

    private final FixedAssetDepreciationMapper mapper;
    private final FixedAssetDepreciationRepository repository;
    private final FixedAssetDepreciationSearchRepository searchRepository;

    public FixedAssetDepreciationBatchService(FixedAssetDepreciationMapper mapper, FixedAssetDepreciationRepository repository, FixedAssetDepreciationSearchRepository searchRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.searchRepository = searchRepository;
    }

    @Override
    public List<FixedAssetDepreciationDTO> save(List<FixedAssetDepreciationDTO> entities) {
        return mapper.toDto(repository.saveAll(mapper.toEntity(entities)));
    }

    @Override
    public void index(List<FixedAssetDepreciationDTO> entities) {

        searchRepository.saveAll(mapper.toEntity(entities));
    }
}
