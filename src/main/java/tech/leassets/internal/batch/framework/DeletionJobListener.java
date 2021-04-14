package tech.leassets.internal.batch.framework;

/*-
 * Leassets Server - Leases and assets management platform
 * Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import tech.leassets.service.LeassetsFileUploadService;
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
