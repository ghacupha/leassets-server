package tech.leassets.service;

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

import tech.leassets.domain.LeassetsMessageToken;
import tech.leassets.domain.*; // for static metamodels
import tech.leassets.repository.LeassetsMessageTokenRepository;
import tech.leassets.repository.search.LeassetsMessageTokenSearchRepository;
import tech.leassets.service.dto.LeassetsMessageTokenCriteria;
import tech.leassets.service.dto.LeassetsMessageTokenDTO;
import tech.leassets.service.mapper.LeassetsMessageTokenMapper;

/**
 * Service for executing complex queries for {@link LeassetsMessageToken} entities in the database.
 * The main input is a {@link LeassetsMessageTokenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeassetsMessageTokenDTO} or a {@link Page} of {@link LeassetsMessageTokenDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeassetsMessageTokenQueryService extends QueryService<LeassetsMessageToken> {

    private final Logger log = LoggerFactory.getLogger(LeassetsMessageTokenQueryService.class);

    private final LeassetsMessageTokenRepository leassetsMessageTokenRepository;

    private final LeassetsMessageTokenMapper leassetsMessageTokenMapper;

    private final LeassetsMessageTokenSearchRepository leassetsMessageTokenSearchRepository;

    public LeassetsMessageTokenQueryService(LeassetsMessageTokenRepository leassetsMessageTokenRepository, LeassetsMessageTokenMapper leassetsMessageTokenMapper, LeassetsMessageTokenSearchRepository leassetsMessageTokenSearchRepository) {
        this.leassetsMessageTokenRepository = leassetsMessageTokenRepository;
        this.leassetsMessageTokenMapper = leassetsMessageTokenMapper;
        this.leassetsMessageTokenSearchRepository = leassetsMessageTokenSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LeassetsMessageTokenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeassetsMessageTokenDTO> findByCriteria(LeassetsMessageTokenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeassetsMessageToken> specification = createSpecification(criteria);
        return leassetsMessageTokenMapper.toDto(leassetsMessageTokenRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LeassetsMessageTokenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeassetsMessageTokenDTO> findByCriteria(LeassetsMessageTokenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeassetsMessageToken> specification = createSpecification(criteria);
        return leassetsMessageTokenRepository.findAll(specification, page)
            .map(leassetsMessageTokenMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeassetsMessageTokenCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeassetsMessageToken> specification = createSpecification(criteria);
        return leassetsMessageTokenRepository.count(specification);
    }

    /**
     * Function to convert {@link LeassetsMessageTokenCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeassetsMessageToken> createSpecification(LeassetsMessageTokenCriteria criteria) {
        Specification<LeassetsMessageToken> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeassetsMessageToken_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), LeassetsMessageToken_.description));
            }
            if (criteria.getTimeSent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeSent(), LeassetsMessageToken_.timeSent));
            }
            if (criteria.getTokenValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTokenValue(), LeassetsMessageToken_.tokenValue));
            }
            if (criteria.getReceived() != null) {
                specification = specification.and(buildSpecification(criteria.getReceived(), LeassetsMessageToken_.received));
            }
            if (criteria.getActioned() != null) {
                specification = specification.and(buildSpecification(criteria.getActioned(), LeassetsMessageToken_.actioned));
            }
            if (criteria.getContentFullyEnqueued() != null) {
                specification = specification.and(buildSpecification(criteria.getContentFullyEnqueued(), LeassetsMessageToken_.contentFullyEnqueued));
            }
        }
        return specification;
    }
}
