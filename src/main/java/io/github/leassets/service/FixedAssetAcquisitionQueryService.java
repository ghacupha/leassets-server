package io.github.leassets.service;

import io.github.leassets.domain.*; // for static metamodels
import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.repository.FixedAssetAcquisitionRepository;
import io.github.leassets.repository.search.FixedAssetAcquisitionSearchRepository;
import io.github.leassets.service.criteria.FixedAssetAcquisitionCriteria;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import io.github.leassets.service.mapper.FixedAssetAcquisitionMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FixedAssetAcquisition} entities in the database.
 * The main input is a {@link FixedAssetAcquisitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FixedAssetAcquisitionDTO} or a {@link Page} of {@link FixedAssetAcquisitionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FixedAssetAcquisitionQueryService extends QueryService<FixedAssetAcquisition> {

    private final Logger log = LoggerFactory.getLogger(FixedAssetAcquisitionQueryService.class);

    private final FixedAssetAcquisitionRepository fixedAssetAcquisitionRepository;

    private final FixedAssetAcquisitionMapper fixedAssetAcquisitionMapper;

    private final FixedAssetAcquisitionSearchRepository fixedAssetAcquisitionSearchRepository;

    public FixedAssetAcquisitionQueryService(
        FixedAssetAcquisitionRepository fixedAssetAcquisitionRepository,
        FixedAssetAcquisitionMapper fixedAssetAcquisitionMapper,
        FixedAssetAcquisitionSearchRepository fixedAssetAcquisitionSearchRepository
    ) {
        this.fixedAssetAcquisitionRepository = fixedAssetAcquisitionRepository;
        this.fixedAssetAcquisitionMapper = fixedAssetAcquisitionMapper;
        this.fixedAssetAcquisitionSearchRepository = fixedAssetAcquisitionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FixedAssetAcquisitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FixedAssetAcquisitionDTO> findByCriteria(FixedAssetAcquisitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FixedAssetAcquisition> specification = createSpecification(criteria);
        return fixedAssetAcquisitionMapper.toDto(fixedAssetAcquisitionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FixedAssetAcquisitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FixedAssetAcquisitionDTO> findByCriteria(FixedAssetAcquisitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FixedAssetAcquisition> specification = createSpecification(criteria);
        return fixedAssetAcquisitionRepository.findAll(specification, page).map(fixedAssetAcquisitionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FixedAssetAcquisitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FixedAssetAcquisition> specification = createSpecification(criteria);
        return fixedAssetAcquisitionRepository.count(specification);
    }

    /**
     * Function to convert {@link FixedAssetAcquisitionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FixedAssetAcquisition> createSpecification(FixedAssetAcquisitionCriteria criteria) {
        Specification<FixedAssetAcquisition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FixedAssetAcquisition_.id));
            }
            if (criteria.getAssetNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetNumber(), FixedAssetAcquisition_.assetNumber));
            }
            if (criteria.getServiceOutletCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getServiceOutletCode(), FixedAssetAcquisition_.serviceOutletCode));
            }
            if (criteria.getAssetTag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetTag(), FixedAssetAcquisition_.assetTag));
            }
            if (criteria.getAssetDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAssetDescription(), FixedAssetAcquisition_.assetDescription));
            }
            if (criteria.getPurchaseDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchaseDate(), FixedAssetAcquisition_.purchaseDate));
            }
            if (criteria.getAssetCategory() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAssetCategory(), FixedAssetAcquisition_.assetCategory));
            }
            if (criteria.getPurchasePrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPurchasePrice(), FixedAssetAcquisition_.purchasePrice));
            }
            if (criteria.getFileUploadToken() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFileUploadToken(), FixedAssetAcquisition_.fileUploadToken));
            }
        }
        return specification;
    }
}
