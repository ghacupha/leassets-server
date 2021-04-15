package io.github.leassets.internal.framework.batch;


import io.github.leassets.internal.framework.batch.HasDataFile;

import java.util.Optional;

public interface BatchPersistentFileUploadService<F> {
    Optional<HasDataFile<F>> findOne(long fileId);
}
