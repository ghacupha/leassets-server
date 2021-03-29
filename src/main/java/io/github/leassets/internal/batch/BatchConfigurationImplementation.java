package io.github.leassets.internal.batch;

import io.github.leassets.internal.batch.framework.DataFileUploadService;
import io.github.leassets.internal.batch.framework.DefaultFileUploadImplementation;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
