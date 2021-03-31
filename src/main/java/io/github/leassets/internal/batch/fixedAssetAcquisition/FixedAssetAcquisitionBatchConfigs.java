package io.github.leassets.internal.batch.fixedAssetAcquisition;

import com.netflix.discovery.converters.Auto;
import io.github.leassets.config.FileUploadsProperties;
import io.github.leassets.internal.Mapping;
import io.github.leassets.internal.batch.framework.DataFileUploadService;
import io.github.leassets.internal.batch.framework.EntityItemsJob;
import io.github.leassets.internal.batch.framework.EntityItemsProcessor;
import io.github.leassets.internal.batch.framework.EntityItemsReader;
import io.github.leassets.internal.batch.framework.EntityListItemsWriter;
import io.github.leassets.internal.excel.ExcelFileDeserializer;
import io.github.leassets.internal.model.FixedAssetAcquisitionEVM;
import io.github.leassets.internal.service.BatchService;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

@Configuration
public class FixedAssetAcquisitionBatchConfigs {

    @Value("#{jobParameters['fileId']}")
    private static long fileId;

    @Value("#{jobParameters['messageToken']}")
    private static String jobUploadToken;

    @Autowired
    private FileUploadsProperties fileUploadsProperties;

    @Autowired
    private ExcelFileDeserializer<FixedAssetAcquisitionEVM> fixedAssetAcquisitionDeserializer;

    @Autowired
    @Qualifier("leassetsFileUploads")
    private DataFileUploadService<LeassetsFileUploadDTO> fileUploadService;

    @Autowired
    private BatchService<FixedAssetAcquisitionDTO> batchService;

    @Autowired
    private Mapping<FixedAssetAcquisitionEVM, FixedAssetAcquisitionDTO> mapping;

    @Bean
    public EntityItemsReader<LeassetsFileUploadDTO, FixedAssetAcquisitionEVM> fixedAssetsEntityItemsReader(
        @Value("#{jobParameters['fileId']}") long fileId
    ) {

        return new EntityItemsReader<>(fixedAssetAcquisitionDeserializer, fileUploadService, fileId, fileUploadsProperties);
    }

    @Bean
    public EntityItemsProcessor<FixedAssetAcquisitionEVM, FixedAssetAcquisitionDTO> fixedAssetsAcquisitionItemsProcessor() {

        return new FixedAssetsAcquisitionEntityItemsProcessor(mapping, jobUploadToken);
    }

    @Bean
    public EntityListItemsWriter<FixedAssetAcquisitionDTO> fixedAssetAcquisitionEntityListItemsWriter() {

        return new EntityListItemsWriter<>(batchService);
    }

    @Bean()
    public Job fixedAssetsAcquisitionPersistenceJob() {

        return new EntityItemsJob("fixedAssetsAcquisitionsPersistenceJob");
    }
}
