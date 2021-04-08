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

import static org.elasticsearch.index.query.QueryBuilders.*;

import io.github.leassets.domain.LeassetsFileUpload;
import io.github.leassets.repository.LeassetsFileUploadRepository;
import io.github.leassets.repository.search.LeassetsFileUploadSearchRepository;
import io.github.leassets.service.LeassetsFileUploadService;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import io.github.leassets.service.mapper.LeassetsFileUploadMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeassetsFileUpload}.
 */
@Service
@Transactional
public class LeassetsFileUploadServiceImpl implements LeassetsFileUploadService {

    private final Logger log = LoggerFactory.getLogger(LeassetsFileUploadServiceImpl.class);

    private final LeassetsFileUploadRepository leassetsFileUploadRepository;

    private final LeassetsFileUploadMapper leassetsFileUploadMapper;

    private final LeassetsFileUploadSearchRepository leassetsFileUploadSearchRepository;

    public LeassetsFileUploadServiceImpl(
        LeassetsFileUploadRepository leassetsFileUploadRepository,
        LeassetsFileUploadMapper leassetsFileUploadMapper,
        LeassetsFileUploadSearchRepository leassetsFileUploadSearchRepository
    ) {
        this.leassetsFileUploadRepository = leassetsFileUploadRepository;
        this.leassetsFileUploadMapper = leassetsFileUploadMapper;
        this.leassetsFileUploadSearchRepository = leassetsFileUploadSearchRepository;
    }

    @Override
    public LeassetsFileUploadDTO save(LeassetsFileUploadDTO leassetsFileUploadDTO) {
        log.debug("Request to save LeassetsFileUpload : {}", leassetsFileUploadDTO);
        LeassetsFileUpload leassetsFileUpload = leassetsFileUploadMapper.toEntity(leassetsFileUploadDTO);
        leassetsFileUpload = leassetsFileUploadRepository.save(leassetsFileUpload);
        LeassetsFileUploadDTO result = leassetsFileUploadMapper.toDto(leassetsFileUpload);
        leassetsFileUploadSearchRepository.save(leassetsFileUpload);
        return result;
    }

    @Override
    public Optional<LeassetsFileUploadDTO> partialUpdate(LeassetsFileUploadDTO leassetsFileUploadDTO) {
        log.debug("Request to partially update LeassetsFileUpload : {}", leassetsFileUploadDTO);

        return leassetsFileUploadRepository
            .findById(leassetsFileUploadDTO.getId())
            .map(
                existingLeassetsFileUpload -> {
                    leassetsFileUploadMapper.partialUpdate(existingLeassetsFileUpload, leassetsFileUploadDTO);
                    return existingLeassetsFileUpload;
                }
            )
            .map(leassetsFileUploadRepository::save)
            .map(
                savedLeassetsFileUpload -> {
                    leassetsFileUploadSearchRepository.save(savedLeassetsFileUpload);

                    return savedLeassetsFileUpload;
                }
            )
            .map(leassetsFileUploadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeassetsFileUploadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeassetsFileUploads");
        return leassetsFileUploadRepository.findAll(pageable).map(leassetsFileUploadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LeassetsFileUploadDTO> findOne(Long id) {
        log.debug("Request to get LeassetsFileUpload : {}", id);
        return leassetsFileUploadRepository.findById(id).map(leassetsFileUploadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeassetsFileUpload : {}", id);
        leassetsFileUploadRepository.deleteById(id);
        leassetsFileUploadSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeassetsFileUploadDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LeassetsFileUploads for query {}", query);
        return leassetsFileUploadSearchRepository.search(queryStringQuery(query), pageable).map(leassetsFileUploadMapper::toDto);
    }
}
