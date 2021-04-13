package io.github.leassets.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import io.github.leassets.domain.CurrencyTable;
import io.github.leassets.repository.CurrencyTableRepository;
import io.github.leassets.repository.search.CurrencyTableSearchRepository;
import io.github.leassets.service.CurrencyTableService;
import io.github.leassets.service.dto.CurrencyTableDTO;
import io.github.leassets.service.mapper.CurrencyTableMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CurrencyTable}.
 */
@Service
@Transactional
public class CurrencyTableServiceImpl implements CurrencyTableService {

    private final Logger log = LoggerFactory.getLogger(CurrencyTableServiceImpl.class);

    private final CurrencyTableRepository currencyTableRepository;

    private final CurrencyTableMapper currencyTableMapper;

    private final CurrencyTableSearchRepository currencyTableSearchRepository;

    public CurrencyTableServiceImpl(
        CurrencyTableRepository currencyTableRepository,
        CurrencyTableMapper currencyTableMapper,
        CurrencyTableSearchRepository currencyTableSearchRepository
    ) {
        this.currencyTableRepository = currencyTableRepository;
        this.currencyTableMapper = currencyTableMapper;
        this.currencyTableSearchRepository = currencyTableSearchRepository;
    }

    @Override
    public CurrencyTableDTO save(CurrencyTableDTO currencyTableDTO) {
        log.debug("Request to save CurrencyTable : {}", currencyTableDTO);
        CurrencyTable currencyTable = currencyTableMapper.toEntity(currencyTableDTO);
        currencyTable = currencyTableRepository.save(currencyTable);
        CurrencyTableDTO result = currencyTableMapper.toDto(currencyTable);
        currencyTableSearchRepository.save(currencyTable);
        return result;
    }

    @Override
    public Optional<CurrencyTableDTO> partialUpdate(CurrencyTableDTO currencyTableDTO) {
        log.debug("Request to partially update CurrencyTable : {}", currencyTableDTO);

        return currencyTableRepository
            .findById(currencyTableDTO.getId())
            .map(
                existingCurrencyTable -> {
                    currencyTableMapper.partialUpdate(existingCurrencyTable, currencyTableDTO);
                    return existingCurrencyTable;
                }
            )
            .map(currencyTableRepository::save)
            .map(
                savedCurrencyTable -> {
                    currencyTableSearchRepository.save(savedCurrencyTable);

                    return savedCurrencyTable;
                }
            )
            .map(currencyTableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CurrencyTableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CurrencyTables");
        return currencyTableRepository.findAll(pageable).map(currencyTableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CurrencyTableDTO> findOne(Long id) {
        log.debug("Request to get CurrencyTable : {}", id);
        return currencyTableRepository.findById(id).map(currencyTableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurrencyTable : {}", id);
        currencyTableRepository.deleteById(id);
        currencyTableSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CurrencyTableDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CurrencyTables for query {}", query);
        return currencyTableSearchRepository.search(queryStringQuery(query), pageable).map(currencyTableMapper::toDto);
    }
}
