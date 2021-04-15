package io.github.leassets.internal.framework.batch;

import java.util.Optional;

public interface BatchPersistentFileUploadService {

    Optional<HasDataFile> findOne(long fileId);
}
