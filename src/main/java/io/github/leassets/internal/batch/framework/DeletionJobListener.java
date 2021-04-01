package io.github.leassets.internal.batch.framework;

import io.github.leassets.service.LeassetsFileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;


@Scope("job")
public class DeletionJobListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(DeletionJobListener.class);
    private final long fileId;
    private final long startUpTime;
    private final LeassetsFileUploadService granularFileUploadService;

    public DeletionJobListener(
        final @Value("#{jobParameters['fileId']}") long fileId,
        final long startUpTime,
        final LeassetsFileUploadService granularFileUploadService
    ) {
        this.fileId = fileId;
        this.startUpTime = startUpTime;
        this.granularFileUploadService = granularFileUploadService;
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
        granularFileUploadService
            .findOne(fileId)
            .ifPresentOrElse(
                file -> {
                    granularFileUploadService.delete(file.getId());
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
