package io.github.leassets.service;

import io.github.leassets.internal.batch.framework.DataFileUploadService;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link io.github.leassets.domain.LeassetsFileUpload}.
 */
public interface LeassetsFileUploadService extends DataFileUploadService<LeassetsFileUploadDTO> {
    /**
     * Save a leassetsFileUpload.
     *
     * @param leassetsFileUploadDTO the entity to save.
     * @return the persisted entity.
     */
    LeassetsFileUploadDTO save(LeassetsFileUploadDTO leassetsFileUploadDTO);

    /**
     * Partially updates a leassetsFileUpload.
     *
     * @param leassetsFileUploadDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LeassetsFileUploadDTO> partialUpdate(LeassetsFileUploadDTO leassetsFileUploadDTO);

    /**
     * Get all the leassetsFileUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeassetsFileUploadDTO> findAll(Pageable pageable);

    /**
     * Get the "id" leassetsFileUpload.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LeassetsFileUploadDTO> findOne(Long id);

    /**
     * Delete the "id" leassetsFileUpload.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the leassetsFileUpload corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeassetsFileUploadDTO> search(String query, Pageable pageable);
}
