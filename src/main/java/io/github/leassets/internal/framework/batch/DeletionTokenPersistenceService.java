package io.github.leassets.internal.framework.batch;

import io.github.leassets.internal.framework.model.TokenDTO;

import java.util.List;
import java.util.Optional;

public interface DeletionTokenPersistenceService<DTO> {

    DTO save(DTO persistentToken);

    Optional<List<DTO>> findAllByUploadToken(String stringToken);
}
