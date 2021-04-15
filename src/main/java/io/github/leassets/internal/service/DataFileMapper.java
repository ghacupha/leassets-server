package io.github.leassets.internal.service;

import io.github.leassets.internal.framework.batch.HasDataFile;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;

import java.util.Optional;

public interface DataFileMapper {

    default Optional<HasDataFile<LeassetsFileUploadDTO>> toHasDataFile(LeassetsFileUploadDTO uploadDTO) {
        return Optional.of(uploadDTO);
    }
}
