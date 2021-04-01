package io.github.leassets.internal.batch.fixedAssetAcquisition;

import io.github.leassets.config.FileUploadsProperties;
import io.github.leassets.internal.batch.ListPartition;
import io.github.leassets.internal.service.FileUploadTokenService;
import io.github.leassets.service.LeassetsFileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class EntityItemsDeletionReader<DTO> implements ItemReader<List<Long>> {
    private final long fileId;

    private final GranularFileUploadService fileUploadService;
    private final FileUploadsProperties fileUploadsProperties;
    private final FileUploadTokenService<BankGuaranteeDTO> fileUploadTokenService;

    // TODO Initialize later, not in the constructor
    private ListPartition<Long> listPartition;

    public BankGuaranteeDeleteItemsReader(
        final @Value("#{jobParameters['fileId']}") long fileId,
        final GranularFileUploadService fileUploadService,
        final FileUploadsProperties fileUploadsProperties,
        final FileUploadTokenService<BankGuaranteeDTO> fileUploadTokenService
    ) {
        this.fileId = fileId;
        this.fileUploadService = fileUploadService;
        this.fileUploadsProperties = fileUploadsProperties;
        this.fileUploadTokenService = fileUploadTokenService;
    }

    @PostConstruct
    private void resetIndex() {
        final List<Long> unProcessedItems = new CopyOnWriteArrayList<>();

        // update list
        fileUploadService
            .findOne(fileId)
            .ifPresent(
                fileUpload -> {
                    String messageToken = fileUpload.getUploadToken();
                    fileUploadTokenService
                        .findAllByUploadToken(messageToken)
                        .ifPresent(
                            entities -> {
                                // TODO pass actual entities for deletion
                                unProcessedItems.addAll(entities.stream().map(BankGuaranteeDTO::getId).collect(Collectors.toList()));
                            }
                        );
                }
            );

        // Going for a big chunk due to expected file size
        listPartition = new ListPartition<>(fileUploadsProperties.getLargeUploads(), unProcessedItems);

        log.info("List items realized : {}", unProcessedItems.size());
    }

    @Override
    public List<Long> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        List<Long> processingList = listPartition.getPartition();

        log.info("Returning list of {} items", processingList.size());

        return processingList.size() == 0 ? null : processingList;
    }
}
