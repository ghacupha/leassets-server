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
package io.github.leassets.internal.batch.fixedAssetAcquisition;

import com.google.common.collect.ImmutableList;
import io.github.leassets.internal.framework.FileUploadsProperties;
import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.internal.excel.ExcelFileDeserializer;
import io.github.leassets.internal.framework.BatchService;
import io.github.leassets.internal.framework.Mapping;
import io.github.leassets.internal.framework.batch.BatchPersistentFileUploadService;
import io.github.leassets.internal.framework.batch.DeletionService;
import io.github.leassets.internal.framework.batch.EntityItemsReader;
import io.github.leassets.internal.framework.service.FileUploadPersistenceService;
import io.github.leassets.internal.framework.service.TokenPersistenceService;
import io.github.leassets.internal.model.FixedAssetAcquisitionEVM;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * This java-based configuration has been designed to be as general as possible with all
 * static string names placed at the top. This is to facilitate reuse for other entities.
 * Where it's not possible to create an abstract configuration at lease this class
 * can be considered a well done template for the others to adhere to.
 */
@Configuration
public class FixedAssetAcquisitionBatchConfigs {

    private static final String PERSISTENCE_JOB_NAME = "fixedAssetAcquisitionListPersistenceJob";
    private static final String DELETION_JOB_NAME = "fixedAssetAcquisitionListDeletionJob";
    private static final String READ_FILE_STEP_NAME = "readFixedAssetAcquisitionListFromFile";
    private static final String DELETION_STEP_NAME = "deleteFixedAssetAcquisitionListFromFile";
    private static final String DELETION_PROCESSOR_NAME = "fixedAssetAcquisitionDeletionProcessor";
    private static final String DELETION_WRITER_NAME = "fixedAssetAcquisitionDeletionWriter";
    private static final String DELETION_READER_NAME = "fixedAssetsAcquisitionDeletionReader";
    private static final String PERSISTENCE_READER_NAME = "fixedAssetAcquisitionListItemReader";
    private static final String PERSISTENCE_PROCESSOR_NAME = "fixedAssetsAcquisitionListItemProcessor";
    private static final String PERSISTENCE_WRITER_NAME = "fixedAssetAcquisitionEntityListItemsWriter";

    @Value("#{jobParameters['fileId']}")
    private static long fileId;

    @Value("#{jobParameters['messageToken']}")
    private static String jobUploadToken;

    @Autowired
    private FileUploadsProperties fileUploadsProperties;

    @Autowired
    private JobExecutionListener persistenceJobListener;

    @Autowired
    private JobExecutionListener deletionJobListener;

    @Autowired
    private ExcelFileDeserializer<FixedAssetAcquisitionEVM> fixedAssetAcquisitionDeserializer;

    @Autowired
    private BatchService<FixedAssetAcquisitionDTO> batchService;

    @Autowired
    private DeletionService<FixedAssetAcquisition> fixedAssetAcquisitionDeletionService;

    @Autowired
    private Mapping<FixedAssetAcquisitionEVM, FixedAssetAcquisitionDTO> mapping;

    @Autowired
    private TokenPersistenceService<FixedAssetAcquisitionDTO, FixedAssetAcquisition> fileUploadTokenService;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BatchPersistentFileUploadService<LeassetsFileUploadDTO> fileUploadService;

    @Bean(PERSISTENCE_READER_NAME)
    @JobScope
    public EntityItemsReader<LeassetsFileUploadDTO, FixedAssetAcquisitionEVM> listItemReader(
        @Value("#{jobParameters['fileId']}") long fileId
    ) {
        return new EntityItemsReader<>(fixedAssetAcquisitionDeserializer, fileUploadService, fileId, fileUploadsProperties);
    }

    @Bean(PERSISTENCE_PROCESSOR_NAME)
    @JobScope
    public ItemProcessor<List<FixedAssetAcquisitionEVM>, List<FixedAssetAcquisitionDTO>> listItemsProcessor(
        @Value("#{jobParameters['messageToken']}") String jobUploadToken
    ) {
        //        return new FixedAssetAcquisitionListItemProcessor(mapping, jobUploadToken);
        return evms ->
            evms.stream().map(mapping::toValue2).peek(d -> d.setFileUploadToken(jobUploadToken)).collect(ImmutableList.toImmutableList());
    }

    @Bean(PERSISTENCE_WRITER_NAME)
    @JobScope
    public EntityListItemsWriter<FixedAssetAcquisitionDTO> listItemsWriter() {
        return new EntityListItemsWriter<>(batchService);
    }

    @Bean(READ_FILE_STEP_NAME)
    public Step readFile() {
        return new ReadFileStep<>(
            READ_FILE_STEP_NAME,
            listItemReader(fileId),
            listItemsProcessor(jobUploadToken),
            listItemsWriter(),
            stepBuilderFactory
        );
    }

    @Bean(PERSISTENCE_JOB_NAME)
    public Job persistenceJob() {
        return new SingleStepEntityJob(PERSISTENCE_JOB_NAME, persistenceJobListener, readFile(), jobBuilderFactory);
    }

    // fixedAssetAcquisitionDeletionJob
    @Bean(DELETION_JOB_NAME)
    public Job fixedAssetAcquisitionDeletionJob() {
        return new SingleStepEntityJob(DELETION_JOB_NAME, deletionJobListener, deleteEntityListFromFile(), jobBuilderFactory);
    }

    // deleteFixedAssetAcquisitionListFromFile step
    @Bean(DELETION_STEP_NAME)
    public Step deleteEntityListFromFile() {
        return new DataDeletionStep<>(
            stepBuilderFactory,
            DELETION_STEP_NAME,
            deletionReader(fileId),
            deletionProcessor(),
            deletionWriter()
        );
    }

    @Bean(DELETION_READER_NAME)
    @JobScope
    public ItemReader<List<Long>> deletionReader(@Value("#{jobParameters['fileId']}") long fileId) {
        return new EntityItemsDeletionReader(fileId, fileUploadService, fileUploadsProperties, fileUploadTokenService);
    }

    @Bean(DELETION_PROCESSOR_NAME)
    public ItemProcessor<List<Long>, List<FixedAssetAcquisition>> deletionProcessor() {
        return new EntityDeletionProcessor<>(fixedAssetAcquisitionDeletionService);
    }

    @Bean(DELETION_WRITER_NAME)
    public ItemWriter<? super List<FixedAssetAcquisition>> deletionWriter() {
        return guarantees -> {};
    }
}
