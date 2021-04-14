package tech.leassets.internal.batch;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.leassets.internal.batch.framework.DataFileUploadService;
import tech.leassets.internal.batch.framework.DefaultFileUploadImplementation;
import tech.leassets.service.dto.LeassetsFileUploadDTO;

/**
 * The developer is expected to provide own implementation of the DataFileUploadService for a given DTO that
 * represents the file-upload from which the data is read
 */
@Configuration
public class BatchConfigurationImplementation {

    @Autowired
    private LeassetsFileUploads<LeassetsFileUploadDTO> leassetsFileUploads;

    /**
     * Implementation of the data-file-upload-service on the underlying system
     *
     * @return
     */
    @Bean
    public DataFileUploadService<LeassetsFileUploadDTO> dataFileUploadService() {
        return new DefaultFileUploadImplementation<>() {
            @Override
            protected DataFileUploadService<LeassetsFileUploadDTO> getFileUploadService() {
                return leassetsFileUploads;
            }
        };
    }
}
