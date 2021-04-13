package tech.leassets.service.impl;

import tech.leassets.service.LeassetsMessageTokenService;
import tech.leassets.domain.LeassetsMessageToken;
import tech.leassets.repository.LeassetsMessageTokenRepository;
import tech.leassets.repository.search.LeassetsMessageTokenSearchRepository;
import tech.leassets.service.dto.LeassetsMessageTokenDTO;
import tech.leassets.service.mapper.LeassetsMessageTokenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LeassetsMessageToken}.
 */
@Service
@Transactional
public class LeassetsMessageTokenServiceImpl implements LeassetsMessageTokenService {

    private final Logger log = LoggerFactory.getLogger(LeassetsMessageTokenServiceImpl.class);

    private final LeassetsMessageTokenRepository leassetsMessageTokenRepository;

    private final LeassetsMessageTokenMapper leassetsMessageTokenMapper;

    private final LeassetsMessageTokenSearchRepository leassetsMessageTokenSearchRepository;

    public LeassetsMessageTokenServiceImpl(LeassetsMessageTokenRepository leassetsMessageTokenRepository, LeassetsMessageTokenMapper leassetsMessageTokenMapper, LeassetsMessageTokenSearchRepository leassetsMessageTokenSearchRepository) {
        this.leassetsMessageTokenRepository = leassetsMessageTokenRepository;
        this.leassetsMessageTokenMapper = leassetsMessageTokenMapper;
        this.leassetsMessageTokenSearchRepository = leassetsMessageTokenSearchRepository;
    }

    @Override
    public LeassetsMessageTokenDTO save(LeassetsMessageTokenDTO leassetsMessageTokenDTO) {
        log.debug("Request to save LeassetsMessageToken : {}", leassetsMessageTokenDTO);
        LeassetsMessageToken leassetsMessageToken = leassetsMessageTokenMapper.toEntity(leassetsMessageTokenDTO);
        leassetsMessageToken = leassetsMessageTokenRepository.save(leassetsMessageToken);
        LeassetsMessageTokenDTO result = leassetsMessageTokenMapper.toDto(leassetsMessageToken);
        leassetsMessageTokenSearchRepository.save(leassetsMessageToken);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeassetsMessageTokenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeassetsMessageTokens");
        return leassetsMessageTokenRepository.findAll(pageable)
            .map(leassetsMessageTokenMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LeassetsMessageTokenDTO> findOne(Long id) {
        log.debug("Request to get LeassetsMessageToken : {}", id);
        return leassetsMessageTokenRepository.findById(id)
            .map(leassetsMessageTokenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeassetsMessageToken : {}", id);
        leassetsMessageTokenRepository.deleteById(id);
        leassetsMessageTokenSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeassetsMessageTokenDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LeassetsMessageTokens for query {}", query);
        return leassetsMessageTokenSearchRepository.search(queryStringQuery(query), pageable)
            .map(leassetsMessageTokenMapper::toDto);
    }
}
