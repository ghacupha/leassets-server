package io.github.leassets.internal.framework.service;

import io.github.leassets.internal.framework.batch.HasDataFile;

import java.util.Optional;

/**
 * This interface is easily implemented by the service that finds and saves file-upload entities to the
 * repository
 */
public interface FileUploadPersistenceService<DTO> {

    Optional<DTO> findOne(long parseLong);

    DTO save(DTO fileUpload);
}
