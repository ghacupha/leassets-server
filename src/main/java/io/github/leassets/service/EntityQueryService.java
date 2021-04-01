package io.github.leassets.service;

import io.github.leassets.internal.service.HasTokenizedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EntityQueryService<DTO, Entity, Criteria> {
    /**
     * Return a {@link List} of {@link DTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    List<DTO> findByCriteria(HasTokenizedCriteria<Criteria> criteria);

    /**
     * Return a {@link Page} of {@link DTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    Page<DTO> findByCriteria(Criteria criteria, Pageable page);

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    long countByCriteria(Criteria criteria);

    /**
     * Function to convert {@link Criteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    Specification<Entity> createSpecification(Criteria criteria);
}
