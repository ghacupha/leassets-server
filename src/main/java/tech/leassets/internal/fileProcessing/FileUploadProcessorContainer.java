package tech.leassets.internal.fileProcessing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// todo loop for every file model type
import static tech.leassets.domain.enumeration.LeassetsFileModelType.CURRENCY_LIST;
import static tech.leassets.domain.enumeration.LeassetsFileModelType.FIXED_ASSET_ACQUISITION;

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

    @Bean("fileUploadProcessorChain")
    public FileUploadProcessorChain fileUploadProcessorChain() {
        FileUploadProcessorChain theChain = new FileUploadProcessorChain();

        // Create the chain, each should match against it's specific key of data-model type
        theChain.addProcessor(new BatchSupportedFileUploadProcessor(jobLauncher, currencyTablePersistenceJob, CURRENCY_LIST));
        theChain.addProcessor(
            new BatchSupportedFileUploadProcessor(jobLauncher, fixedAssetAcquisitionListPersistenceJob, FIXED_ASSET_ACQUISITION)
        );
        theChain.addProcessor(
            new BatchSupportedFileUploadProcessor(jobLauncher, fixedAssetAcquisitionListDeletionJob, FIXED_ASSET_ACQUISITION)
        );

        return theChain;
    }

}
