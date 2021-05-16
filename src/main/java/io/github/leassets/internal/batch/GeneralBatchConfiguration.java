package io.github.leassets.internal.batch;

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

import io.github.leassets.internal.framework.batch.DeletionJobListener;
import io.github.leassets.internal.framework.service.FileUploadDeletionService;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralBatchConfiguration {

    @Autowired
    private FileUploadDeletionService<LeassetsFileUploadDTO> fileUploadDeletionService;


    @Bean("deletionJobListener")
    @JobScope
    public JobExecutionListener deletionJobListener(@Value("#{jobParameters['fileId']}") long fileId, @Value("#{jobParameters['startUpTime']}") long startUpTime) {
        return new DeletionJobListener(fileId, startUpTime, fileUploadDeletionService);
    }
}