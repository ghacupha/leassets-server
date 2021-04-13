package tech.leassets.service;

import tech.leassets.service.dto.LeassetsMessageTokenDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link tech.leassets.domain.LeassetsMessageToken}.
 */
public interface LeassetsMessageTokenService {

    /**
     * Save a leassetsMessageToken.
     *
     * @param leassetsMessageTokenDTO the entity to save.
     * @return the persisted entity.
     */
    LeassetsMessageTokenDTO save(LeassetsMessageTokenDTO leassetsMessageTokenDTO);

    /**
     * Get all the leassetsMessageTokens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeassetsMessageTokenDTO> findAll(Pageable pageable);


    /**
     * Get the "id" leassetsMessageToken.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LeassetsMessageTokenDTO> findOne(Long id);

    /**
     * Delete the "id" leassetsMessageToken.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the leassetsMessageToken corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeassetsMessageTokenDTO> search(String query, Pageable pageable);
}
