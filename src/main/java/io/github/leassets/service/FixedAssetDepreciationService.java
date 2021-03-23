package io.github.leassets.service;

import io.github.leassets.service.dto.FixedAssetDepreciationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link io.github.leassets.domain.FixedAssetDepreciation}.
 */
public interface FixedAssetDepreciationService {
    /**
     * Save a fixedAssetDepreciation.
     *
     * @param fixedAssetDepreciationDTO the entity to save.
     * @return the persisted entity.
     */
    FixedAssetDepreciationDTO save(FixedAssetDepreciationDTO fixedAssetDepreciationDTO);

    /**
     * Partially updates a fixedAssetDepreciation.
     *
     * @param fixedAssetDepreciationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FixedAssetDepreciationDTO> partialUpdate(FixedAssetDepreciationDTO fixedAssetDepreciationDTO);

    /**
     * Get all the fixedAssetDepreciations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FixedAssetDepreciationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fixedAssetDepreciation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FixedAssetDepreciationDTO> findOne(Long id);

    /**
     * Delete the "id" fixedAssetDepreciation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the fixedAssetDepreciation corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FixedAssetDepreciationDTO> search(String query, Pageable pageable);
}
