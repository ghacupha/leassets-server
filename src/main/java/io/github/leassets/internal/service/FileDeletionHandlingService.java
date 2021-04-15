package io.github.leassets.internal.service;

import io.github.leassets.domain.enumeration.LeassetsFileModelType;
import io.github.leassets.internal.framework.fileProcessing.FileUploadProcessorChain;
import io.github.leassets.internal.framework.service.HandlingService;
import io.github.leassets.internal.model.FileNotification;
import io.github.leassets.service.LeassetsFileTypeService;
import io.github.leassets.service.LeassetsFileUploadService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

/**
 * This handler takes a file-id and processes related workflows for deletion of file related data
 */
@Service("fileDeletionHandlingService")
public class FileDeletionHandlingService implements HandlingService<Long> {
    private final LeassetsFileTypeService fileTypeService;
    private final LeassetsFileUploadService fileUploadService;

    private final FileUploadProcessorChain fileUploadDeletionProcessorChain;

    public FileDeletionHandlingService(
        final LeassetsFileTypeService fileTypeService,
        final LeassetsFileUploadService fileUploadService,
        final @Qualifier("fileUploadDeletionProcessorChain")
            FileUploadProcessorChain fileUploadDeletionProcessorChain
    ) {
        this.fileTypeService = fileTypeService;
        this.fileUploadService = fileUploadService;
        this.fileUploadDeletionProcessorChain = fileUploadDeletionProcessorChain;
    }

    /**
     * Returns an instance of this after handling the payload issued
     *
     * @param payload The item being handled
     */
    @Override
    @Async
    public void handle(final Long payload) {
        fileUploadService
            .findOne(payload)
            .ifPresent(
                file -> {
                    final AtomicReference<LeassetsFileModelType> type = new AtomicReference<>();
                    fileTypeService
                        .findOne(file.getLeassetsFileTypeId())
                        .ifPresent(
                            fileType -> {
                                type.set(fileType.getLeassetsfileType());

                                fileUploadDeletionProcessorChain.apply(
                                    file,
                                    FileNotification
                                        .builder()
                                        .fileId(String.valueOf(file.getId()))
                                        .filename(file.getFileName())
                                        .messageToken(file.getUploadToken())
                                        .timestamp(System.currentTimeMillis())
                                        .leassetsfileModelType(type.get())
                                        .build()
                                );
                            }
                        );
                }
            );
    }
}
