package tech.leassets.service;

import tech.leassets.service.dto.LeassetsFileUploadDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link tech.leassets.domain.LeassetsFileUpload}.
 */
public interface LeassetsFileUploadService {

    /**
     * Save a leassetsFileUpload.
     *
     * @param leassetsFileUploadDTO the entity to save.
     * @return the persisted entity.
     */
    LeassetsFileUploadDTO save(LeassetsFileUploadDTO leassetsFileUploadDTO);

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
