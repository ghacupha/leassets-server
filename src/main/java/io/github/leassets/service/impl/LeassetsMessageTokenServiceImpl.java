package io.github.leassets.service.impl;

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

import io.github.leassets.service.LeassetsMessageTokenService;
import io.github.leassets.domain.LeassetsMessageToken;
import io.github.leassets.repository.LeassetsMessageTokenRepository;
import io.github.leassets.repository.search.LeassetsMessageTokenSearchRepository;
import io.github.leassets.service.dto.LeassetsMessageTokenDTO;
import io.github.leassets.service.mapper.LeassetsMessageTokenMapper;
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
