package io.github.leassets.internal.service;

import io.github.leassets.internal.framework.batch.BatchPersistentFileUploadService;
import io.github.leassets.internal.framework.batch.HasDataFile;
import io.github.leassets.service.LeassetsFileUploadService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BatchPersistentFileUploadServiceImpl implements BatchPersistentFileUploadService {

    private final LeassetsFileUploadService fileUploadService;

    public BatchPersistentFileUploadServiceImpl(LeassetsFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Override
    public Optional<HasDataFile> findOne(long fileId) {

        AtomicReference<Optional<HasDataFile>> fileUpload = new AtomicReference<>();

        fileUploadService.findOne(fileId).ifPresent(optionalFile -> fileUpload.set(Optional.of(optionalFile)));

        return fileUpload.get();
    }
}
