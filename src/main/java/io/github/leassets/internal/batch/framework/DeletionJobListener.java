package io.github.leassets.internal.batch.framework;

import io.github.leassets.service.LeassetsFileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

/**
 * Job-scoped listener for deletion processors. The listener provides common notifications
 * for all deletion steps. Deletion processes are also sequential in nature; its therefore
 * risky to delete the data-file before related entities are actually deleted from the system.
 * The batch process therefore depends on this listener to trigger deletion of the actual file
 * upload entity, and in particular the associated data file.
 *
 * TODO: Create several service for processing file-upload deletion
 *
 */
@Scope("job")
public class DeletionJobListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(DeletionJobListener.class);
    private final long fileId;
    private final long startUpTime;
    private final LeassetsFileUploadService fileUploadService;

    public DeletionJobListener(
        final @Value("#{jobParameters['fileId']}") long fileId,
        final long startUpTime,
        final LeassetsFileUploadService fileUploadService
    ) {
        this.fileId = fileId;
        this.startUpTime = startUpTime;
        this.fileUploadService = fileUploadService;
    }

    /**
     * Callback before a job executes.
     *
     * @param jobExecution the current {@link JobExecution}
     */
    @Override
    public void beforeJob(final JobExecution jobExecution) {
        log.info(
            "Commencing deletion sequence ...   New job id : {} has started for file id : {}, with start time : {}",
            jobExecution.getJobId(),
            fileId,
            startUpTime
        );
    }

    /**
     * Callback after completion of a job. Called after both both successful and failed executions. To perform logic on a particular status, use "if (jobExecution.getStatus() == BatchStatus.X)".
     *
     * @param jobExecution the current {@link JobExecution}
     */
    @Override
    public void afterJob(final JobExecution jobExecution) {
        // delete the physical file from the DB
        fileUploadService
            .findOne(fileId)
            .ifPresentOrElse(
                file -> {
                    fileUploadService.delete(file.getId());
                },
                () -> {
                    throw new IllegalArgumentException("File-Id " + fileId + "doth not exist");
                }
            );

        String exitStatus = jobExecution.getExitStatus().getExitCode();

        log.info(
            "Job Id {}, for file-id : {} completed in : {}ms with status {}",
            jobExecution.getJobId(),
            fileId,
            System.currentTimeMillis() - startUpTime,
            exitStatus
        );
    }
}
