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

import io.github.leassets.service.FixedAssetAcquisitionService;
import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.repository.FixedAssetAcquisitionRepository;
import io.github.leassets.repository.search.FixedAssetAcquisitionSearchRepository;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import io.github.leassets.service.mapper.FixedAssetAcquisitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FixedAssetAcquisition}.
 */
@Service
@Transactional
public class FixedAssetAcquisitionServiceImpl implements FixedAssetAcquisitionService {

    private final Logger log = LoggerFactory.getLogger(FixedAssetAcquisitionServiceImpl.class);

    private final FixedAssetAcquisitionRepository fixedAssetAcquisitionRepository;

    private final FixedAssetAcquisitionMapper fixedAssetAcquisitionMapper;

    private final FixedAssetAcquisitionSearchRepository fixedAssetAcquisitionSearchRepository;

    public FixedAssetAcquisitionServiceImpl(FixedAssetAcquisitionRepository fixedAssetAcquisitionRepository, FixedAssetAcquisitionMapper fixedAssetAcquisitionMapper, FixedAssetAcquisitionSearchRepository fixedAssetAcquisitionSearchRepository) {
        this.fixedAssetAcquisitionRepository = fixedAssetAcquisitionRepository;
        this.fixedAssetAcquisitionMapper = fixedAssetAcquisitionMapper;
        this.fixedAssetAcquisitionSearchRepository = fixedAssetAcquisitionSearchRepository;
    }

    @Override
    public FixedAssetAcquisitionDTO save(FixedAssetAcquisitionDTO fixedAssetAcquisitionDTO) {
        log.debug("Request to save FixedAssetAcquisition : {}", fixedAssetAcquisitionDTO);
        FixedAssetAcquisition fixedAssetAcquisition = fixedAssetAcquisitionMapper.toEntity(fixedAssetAcquisitionDTO);
        fixedAssetAcquisition = fixedAssetAcquisitionRepository.save(fixedAssetAcquisition);
        FixedAssetAcquisitionDTO result = fixedAssetAcquisitionMapper.toDto(fixedAssetAcquisition);
        fixedAssetAcquisitionSearchRepository.save(fixedAssetAcquisition);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FixedAssetAcquisitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FixedAssetAcquisitions");
        return fixedAssetAcquisitionRepository.findAll(pageable)
            .map(fixedAssetAcquisitionMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FixedAssetAcquisitionDTO> findOne(Long id) {
        log.debug("Request to get FixedAssetAcquisition : {}", id);
        return fixedAssetAcquisitionRepository.findById(id)
            .map(fixedAssetAcquisitionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FixedAssetAcquisition : {}", id);
        fixedAssetAcquisitionRepository.deleteById(id);
        fixedAssetAcquisitionSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FixedAssetAcquisitionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FixedAssetAcquisitions for query {}", query);
        return fixedAssetAcquisitionSearchRepository.search(queryStringQuery(query), pageable)
            .map(fixedAssetAcquisitionMapper::toDto);
    }
}
