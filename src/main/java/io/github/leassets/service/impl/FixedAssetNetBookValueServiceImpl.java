package io.github.leassets.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import io.github.leassets.domain.FixedAssetNetBookValue;
import io.github.leassets.repository.FixedAssetNetBookValueRepository;
import io.github.leassets.repository.search.FixedAssetNetBookValueSearchRepository;
import io.github.leassets.service.FixedAssetNetBookValueService;
import io.github.leassets.service.dto.FixedAssetNetBookValueDTO;
import io.github.leassets.service.mapper.FixedAssetNetBookValueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FixedAssetNetBookValue}.
 */
@Service
@Transactional
public class FixedAssetNetBookValueServiceImpl implements FixedAssetNetBookValueService {

    private final Logger log = LoggerFactory.getLogger(FixedAssetNetBookValueServiceImpl.class);

    private final FixedAssetNetBookValueRepository fixedAssetNetBookValueRepository;

    private final FixedAssetNetBookValueMapper fixedAssetNetBookValueMapper;

    private final FixedAssetNetBookValueSearchRepository fixedAssetNetBookValueSearchRepository;

    public FixedAssetNetBookValueServiceImpl(
        FixedAssetNetBookValueRepository fixedAssetNetBookValueRepository,
        FixedAssetNetBookValueMapper fixedAssetNetBookValueMapper,
        FixedAssetNetBookValueSearchRepository fixedAssetNetBookValueSearchRepository
    ) {
        this.fixedAssetNetBookValueRepository = fixedAssetNetBookValueRepository;
        this.fixedAssetNetBookValueMapper = fixedAssetNetBookValueMapper;
        this.fixedAssetNetBookValueSearchRepository = fixedAssetNetBookValueSearchRepository;
    }

    @Override
    public FixedAssetNetBookValueDTO save(FixedAssetNetBookValueDTO fixedAssetNetBookValueDTO) {
        log.debug("Request to save FixedAssetNetBookValue : {}", fixedAssetNetBookValueDTO);
        FixedAssetNetBookValue fixedAssetNetBookValue = fixedAssetNetBookValueMapper.toEntity(fixedAssetNetBookValueDTO);
        fixedAssetNetBookValue = fixedAssetNetBookValueRepository.save(fixedAssetNetBookValue);
        FixedAssetNetBookValueDTO result = fixedAssetNetBookValueMapper.toDto(fixedAssetNetBookValue);
        fixedAssetNetBookValueSearchRepository.save(fixedAssetNetBookValue);
        return result;
    }

    @Override
    public Optional<FixedAssetNetBookValueDTO> partialUpdate(FixedAssetNetBookValueDTO fixedAssetNetBookValueDTO) {
        log.debug("Request to partially update FixedAssetNetBookValue : {}", fixedAssetNetBookValueDTO);

        return fixedAssetNetBookValueRepository
            .findById(fixedAssetNetBookValueDTO.getId())
            .map(
                existingFixedAssetNetBookValue -> {
                    fixedAssetNetBookValueMapper.partialUpdate(existingFixedAssetNetBookValue, fixedAssetNetBookValueDTO);
                    return existingFixedAssetNetBookValue;
                }
            )
            .map(fixedAssetNetBookValueRepository::save)
            .map(
                savedFixedAssetNetBookValue -> {
                    fixedAssetNetBookValueSearchRepository.save(savedFixedAssetNetBookValue);

                    return savedFixedAssetNetBookValue;
                }
            )
            .map(fixedAssetNetBookValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FixedAssetNetBookValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FixedAssetNetBookValues");
        return fixedAssetNetBookValueRepository.findAll(pageable).map(fixedAssetNetBookValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FixedAssetNetBookValueDTO> findOne(Long id) {
        log.debug("Request to get FixedAssetNetBookValue : {}", id);
        return fixedAssetNetBookValueRepository.findById(id).map(fixedAssetNetBookValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FixedAssetNetBookValue : {}", id);
        fixedAssetNetBookValueRepository.deleteById(id);
        fixedAssetNetBookValueSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FixedAssetNetBookValueDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FixedAssetNetBookValues for query {}", query);
        return fixedAssetNetBookValueSearchRepository.search(queryStringQuery(query), pageable).map(fixedAssetNetBookValueMapper::toDto);
    }
}
