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

import io.github.leassets.internal.framework.fileProcessing.BatchSupportedFileUploadProcessor;
import io.github.leassets.internal.framework.fileProcessing.FileUploadProcessorChain;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// todo loop for every file model type
import static io.github.leassets.domain.enumeration.LeassetsFileModelType.*;


/**
 * This object maintains a list of all existing processors. This is a short in the dark about automatically configuring the chain at start up
 */
@Configuration
public class FileUploadProcessorContainer {

    @Autowired
    private JobLauncher jobLauncher;

    // todo auto-wire each job for each data model type
    @Autowired
    @Qualifier("currencyTablePersistenceJob")
    private Job currencyTablePersistenceJob;

    @Autowired
    @Qualifier("fixedAssetAcquisitionListPersistenceJob")
    private Job fixedAssetAcquisitionListPersistenceJob;

    @Autowired
    @Qualifier("fixedAssetAcquisitionListDeletionJob")
    private Job fixedAssetAcquisitionListDeletionJob;

    @Autowired
    @Qualifier("fixedAssetDepreciationListPersistenceJob")
    private Job fixedAssetDepreciationListPersistenceJob;

    @Autowired
    @Qualifier("fixedAssetDepreciationListDeletionJob")
    private Job fixedAssetDepreciationListDeletionJob;

    @Bean("fileUploadProcessorChain")
    public FileUploadProcessorChain fileUploadProcessorChain() {

        FileUploadProcessorChain theChain = new FileUploadProcessorChain();

        // Create the chain, each should match against it's specific key of data-model type
        theChain.addProcessor(new BatchSupportedFileUploadProcessor(jobLauncher, currencyTablePersistenceJob, CURRENCY_LIST));
        theChain.addProcessor(new BatchSupportedFileUploadProcessor(jobLauncher, fixedAssetAcquisitionListPersistenceJob, FIXED_ASSET_ACQUISITION));
        theChain.addProcessor(new BatchSupportedFileUploadProcessor(jobLauncher, fixedAssetDepreciationListPersistenceJob, FIXED_ASSET_DEPRECIATION));
        return theChain;
    }

    // configuration for deletion jobs
    @Bean("fileUploadDeletionProcessorChain")
    public FileUploadProcessorChain fileUploadDeletionProcessorChain() {
        FileUploadProcessorChain theChain = new FileUploadProcessorChain();

        theChain.addProcessor(new BatchSupportedFileUploadProcessor(jobLauncher, fixedAssetAcquisitionListDeletionJob, FIXED_ASSET_ACQUISITION));
        theChain.addProcessor(new BatchSupportedFileUploadProcessor(jobLauncher, fixedAssetDepreciationListDeletionJob, FIXED_ASSET_DEPRECIATION));

        return theChain;
    }

}
