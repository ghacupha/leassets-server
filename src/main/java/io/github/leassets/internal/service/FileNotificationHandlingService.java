package io.github.leassets.internal.service;

import static io.github.leassets.internal.AppConstants.PROCESSED_TOKENS;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.leassets.domain.LeassetsMessageToken;
import io.github.leassets.internal.fileProcessing.FileUploadProcessorChain;
import io.github.leassets.internal.model.FileNotification;
import io.github.leassets.internal.util.TokenGenerator;
import io.github.leassets.service.LeassetsFileUploadService;
import io.github.leassets.service.LeassetsMessageTokenService;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import io.github.leassets.service.dto.LeassetsMessageTokenDTO;
import io.github.leassets.service.mapper.LeassetsMessageTokenMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * This is a service that handles file-notification asynchronously.
 *
 */
@Service("fileNotificationHandlingService")
public class FileNotificationHandlingService implements HandlingService<FileNotification> {

    public static Logger log = LoggerFactory.getLogger(FileNotificationHandlingService.class);

    private final TokenGenerator tokenGenerator;
    private final LeassetsMessageTokenService messageTokenService;
    private final LeassetsMessageTokenMapper messageTokenMapper;
    private final LeassetsFileUploadService fileUploadService;
    private final FileUploadProcessorChain fileUploadProcessorChain;

    public FileNotificationHandlingService(
        TokenGenerator tokenGenerator,
        LeassetsMessageTokenService messageTokenService,
        LeassetsMessageTokenMapper messageTokenMapper,
        LeassetsFileUploadService fileUploadService,
        FileUploadProcessorChain fileUploadProcessorChain
    ) {
        this.tokenGenerator = tokenGenerator;
        this.messageTokenService = messageTokenService;
        this.messageTokenMapper = messageTokenMapper;
        this.fileUploadService = fileUploadService;
        this.fileUploadProcessorChain = fileUploadProcessorChain;
    }

    @Override
    @Async
    public void handle(FileNotification payload) {
        log.info("File notification received for: {}", payload.getFilename());

        // Generate token before getting timestamps
        String token = getToken(payload);

        long timestamp = System.currentTimeMillis();
        payload.setTimestamp(timestamp);

        // @formatter:off
        LeassetsMessageToken messageToken = new LeassetsMessageToken()
            .tokenValue(token)
            .description(payload.getDescription())
            .timeSent(timestamp);
        // @formatter:on

        if (messageToken != null) {
            payload.setMessageToken(messageToken.getTokenValue());
        }

        LeassetsFileUploadDTO fileUpload = fileUploadService
            .findOne(Long.parseLong(payload.getFileId()))
            .orElseThrow(() -> new IllegalArgumentException("Id # : " + payload.getFileId() + " does not exist"));

        log.debug("FileUploadDTO object fetched from DB with id: {}", fileUpload.getId());
        if (!PROCESSED_TOKENS.contains(payload.getMessageToken())) {
            log.debug("Processing message with token {}", payload.getMessageToken());
            List<LeassetsFileUploadDTO> processedFiles = fileUploadProcessorChain.apply(fileUpload, payload);
            fileUpload.setUploadProcessed(true);
            fileUpload.setUploadSuccessful(true);
            fileUpload.setUploadToken(token);
            // Explicitly persist new status
            fileUploadService.save(fileUpload);
            PROCESSED_TOKENS.add(payload.getMessageToken());
        } else {
            log.info("Skipped upload of processed files {}", payload.getFilename());
        }

        LeassetsMessageTokenDTO dto = messageTokenService.save(messageTokenMapper.toDto(messageToken));
        dto.setContentFullyEnqueued(true);
    }

    private String getToken(FileNotification payload) {
        String token = "";
        try {
            token = tokenGenerator.md5Digest(payload);
        } catch (JsonProcessingException e) {
            log.error("The service has failed to create a message-token and has been aborted : ", e);
        }
        return token;
    }
}
