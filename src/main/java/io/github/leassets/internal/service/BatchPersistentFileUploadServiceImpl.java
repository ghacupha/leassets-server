package io.github.leassets.internal.service;

import io.github.leassets.internal.framework.batch.BatchPersistentFileUploadService;
import io.github.leassets.internal.framework.batch.HasDataFile;
import io.github.leassets.service.LeassetsFileUploadService;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BatchPersistentFileUploadServiceImpl implements BatchPersistentFileUploadService<LeassetsFileUploadDTO> {

    private final LeassetsFileUploadService fileUploadService;
    private final DataFileMapper mapper;

    public BatchPersistentFileUploadServiceImpl(LeassetsFileUploadService fileUploadService, DataFileMapper mapper) {
        this.fileUploadService = fileUploadService;
        this.mapper = mapper;
    }

    @Override
    public Optional<HasDataFile<LeassetsFileUploadDTO>> findOne(long fileId) {

        AtomicReference<Optional<HasDataFile<LeassetsFileUploadDTO>>> fileUpload = new AtomicReference<>();

        fileUploadService.findOne(fileId).ifPresent(optionalFile -> fileUpload.set(mapper.toHasDataFile(optionalFile)));

        return fileUpload.get();
    }
}
