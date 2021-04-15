package io.github.leassets.internal.service;

import io.github.leassets.internal.framework.batch.HasDataFile;
import io.github.leassets.internal.framework.service.FileUploadPersistenceService;
import io.github.leassets.service.LeassetsFileUploadService;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("fileUploadPersistenceService")
public class FileUploadPersistenceServiceImpl implements FileUploadPersistenceService<LeassetsFileUploadDTO> {

    private final LeassetsFileUploadService leassetsFileUploadService;

    public FileUploadPersistenceServiceImpl(LeassetsFileUploadService leassetsFileUploadService) {
        this.leassetsFileUploadService = leassetsFileUploadService;
    }

    @Override
    public Optional<LeassetsFileUploadDTO> findOne(long fileUploadDTOId) {
        return leassetsFileUploadService.findOne(fileUploadDTOId);
    }

    @Override
    public LeassetsFileUploadDTO save(LeassetsFileUploadDTO fileUpload) {
        return leassetsFileUploadService.save(fileUpload);
    }
}
