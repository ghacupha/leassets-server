package io.github.leassets.service;

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

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.leassets.domain.LeassetsFileUpload;
import io.github.leassets.domain.*; // for static metamodels
import io.github.leassets.repository.LeassetsFileUploadRepository;
import io.github.leassets.repository.search.LeassetsFileUploadSearchRepository;
import io.github.leassets.service.dto.LeassetsFileUploadCriteria;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import io.github.leassets.service.mapper.LeassetsFileUploadMapper;

/**
 * Service for executing complex queries for {@link LeassetsFileUpload} entities in the database.
 * The main input is a {@link LeassetsFileUploadCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeassetsFileUploadDTO} or a {@link Page} of {@link LeassetsFileUploadDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeassetsFileUploadQueryService extends QueryService<LeassetsFileUpload> {

    private final Logger log = LoggerFactory.getLogger(LeassetsFileUploadQueryService.class);

    private final LeassetsFileUploadRepository leassetsFileUploadRepository;

    private final LeassetsFileUploadMapper leassetsFileUploadMapper;

    private final LeassetsFileUploadSearchRepository leassetsFileUploadSearchRepository;

    public LeassetsFileUploadQueryService(LeassetsFileUploadRepository leassetsFileUploadRepository, LeassetsFileUploadMapper leassetsFileUploadMapper, LeassetsFileUploadSearchRepository leassetsFileUploadSearchRepository) {
        this.leassetsFileUploadRepository = leassetsFileUploadRepository;
        this.leassetsFileUploadMapper = leassetsFileUploadMapper;
        this.leassetsFileUploadSearchRepository = leassetsFileUploadSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LeassetsFileUploadDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeassetsFileUploadDTO> findByCriteria(LeassetsFileUploadCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeassetsFileUpload> specification = createSpecification(criteria);
        return leassetsFileUploadMapper.toDto(leassetsFileUploadRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LeassetsFileUploadDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeassetsFileUploadDTO> findByCriteria(LeassetsFileUploadCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeassetsFileUpload> specification = createSpecification(criteria);
        return leassetsFileUploadRepository.findAll(specification, page)
            .map(leassetsFileUploadMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeassetsFileUploadCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeassetsFileUpload> specification = createSpecification(criteria);
        return leassetsFileUploadRepository.count(specification);
    }

    /**
     * Function to convert {@link LeassetsFileUploadCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeassetsFileUpload> createSpecification(LeassetsFileUploadCriteria criteria) {
        Specification<LeassetsFileUpload> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeassetsFileUpload_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), LeassetsFileUpload_.description));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), LeassetsFileUpload_.fileName));
            }
            if (criteria.getPeriodFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodFrom(), LeassetsFileUpload_.periodFrom));
            }
            if (criteria.getPeriodTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodTo(), LeassetsFileUpload_.periodTo));
            }
            if (criteria.getLeassetsFileTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeassetsFileTypeId(), LeassetsFileUpload_.leassetsFileTypeId));
            }
            if (criteria.getUploadSuccessful() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadSuccessful(), LeassetsFileUpload_.uploadSuccessful));
            }
            if (criteria.getUploadProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadProcessed(), LeassetsFileUpload_.uploadProcessed));
            }
            if (criteria.getUploadToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUploadToken(), LeassetsFileUpload_.uploadToken));
            }
        }
        return specification;
    }
}
