package io.github.leassets.internal.fileProcessing;

import io.github.leassets.domain.enumeration.LeassetsFileModelType;
import io.github.leassets.internal.model.framework.FileNotification;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

/**
 * This class is create to enable code reuse where the only parameter that changes the file-model-type
 */
public class BatchSupportedFileUploadProcessor implements FileUploadProcessor<LeassetsFileUploadDTO> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BatchSupportedFileUploadProcessor.class);

    private final JobLauncher jobLauncher;
    public final Job batchJob;
    private final LeassetsFileModelType fileModelType;

    public BatchSupportedFileUploadProcessor(final JobLauncher jobLauncher, final Job batchJob, final LeassetsFileModelType fileModelType) {
        this.jobLauncher = jobLauncher;
        this.batchJob = batchJob;
        this.fileModelType = fileModelType;
    }

    @Override
    public LeassetsFileUploadDTO processFileUpload(final LeassetsFileUploadDTO fileUpload, final FileNotification fileNotification) {
        if (fileNotification.getleassetsfileModelType() == fileModelType) {
            log.debug("File-upload type confirmed commencing process...");

            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addLong("fileId", fileUpload.getId());
            jobParametersBuilder.addLong("startUpTime", fileNotification.getTimestamp());
            jobParametersBuilder.addString("messageToken", fileNotification.getMessageToken());

            try {
                jobLauncher.run(batchJob, jobParametersBuilder.toJobParameters());
            } catch (
                JobExecutionAlreadyRunningException
                | JobRestartException
                | JobParametersInvalidException
                | JobInstanceAlreadyCompleteException e
            ) {
                e.printStackTrace();
            }
        } else {
            log.debug("File upload inconsistent with the data model supported by this processor");
        }

        return fileUpload;
    }
}
