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

import tech.leassets.domain.LeassetsFileType;
import tech.leassets.domain.*; // for static metamodels
import tech.leassets.repository.LeassetsFileTypeRepository;
import tech.leassets.repository.search.LeassetsFileTypeSearchRepository;
import tech.leassets.service.dto.LeassetsFileTypeCriteria;

/**
 * Service for executing complex queries for {@link LeassetsFileType} entities in the database.
 * The main input is a {@link LeassetsFileTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeassetsFileType} or a {@link Page} of {@link LeassetsFileType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeassetsFileTypeQueryService extends QueryService<LeassetsFileType> {

    private final Logger log = LoggerFactory.getLogger(LeassetsFileTypeQueryService.class);

    private final LeassetsFileTypeRepository leassetsFileTypeRepository;

    private final LeassetsFileTypeSearchRepository leassetsFileTypeSearchRepository;

    public LeassetsFileTypeQueryService(LeassetsFileTypeRepository leassetsFileTypeRepository, LeassetsFileTypeSearchRepository leassetsFileTypeSearchRepository) {
        this.leassetsFileTypeRepository = leassetsFileTypeRepository;
        this.leassetsFileTypeSearchRepository = leassetsFileTypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LeassetsFileType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeassetsFileType> findByCriteria(LeassetsFileTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeassetsFileType> specification = createSpecification(criteria);
        return leassetsFileTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeassetsFileType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeassetsFileType> findByCriteria(LeassetsFileTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeassetsFileType> specification = createSpecification(criteria);
        return leassetsFileTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeassetsFileTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeassetsFileType> specification = createSpecification(criteria);
        return leassetsFileTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link LeassetsFileTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeassetsFileType> createSpecification(LeassetsFileTypeCriteria criteria) {
        Specification<LeassetsFileType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeassetsFileType_.id));
            }
            if (criteria.getLeassetsFileTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeassetsFileTypeName(), LeassetsFileType_.leassetsFileTypeName));
            }
            if (criteria.getLeassetsFileMediumType() != null) {
                specification = specification.and(buildSpecification(criteria.getLeassetsFileMediumType(), LeassetsFileType_.leassetsFileMediumType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), LeassetsFileType_.description));
            }
            if (criteria.getLeassetsfileType() != null) {
                specification = specification.and(buildSpecification(criteria.getLeassetsfileType(), LeassetsFileType_.leassetsfileType));
            }
        }
        return specification;
    }
}
