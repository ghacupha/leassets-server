package io.github.leassets.internal.batch.framework;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * This is a marker interface whose client manages persistence for the file-upload in the
 * system
 *
 * @param <T> Type of file-upload data transfer object
 */
public interface DataFileUploadService<T> {
    /**
     * Save a fileUpload.
     *
     * @param fileUploadDTO the entity to save.
     * @return the persisted entity.
     */
    T save(T fileUploadDTO);

    /**
     * Partially updates a fileUpload.
     *
     * @param fileUploadDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<T> partialUpdate(T fileUploadDTO);

    /**
     * Get all the fileUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<? extends HasDataFile> findAll(Pageable pageable);

    /**
     * Get the "id" fileUpload.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<? extends HasDataFile> findOne(Long id);

    /**
     * Delete the "id" fileUpload.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the fileUpload corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<? extends HasDataFile> search(String query, Pageable pageable);
}
