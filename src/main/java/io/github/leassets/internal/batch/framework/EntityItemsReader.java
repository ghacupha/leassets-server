package io.github.leassets.internal.batch.framework;

import io.github.leassets.config.FileUploadsProperties;
import io.github.leassets.internal.excel.ExcelFileDeserializer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @param <F> Type of file-upload data transfer object
 * @param <EVM> Type of object represented in a row of the file in the data transfer object
 */
public class EntityItemsReader<F, EVM> implements ItemReader<List<EVM>> {

    private static final Logger log = LoggerFactory.getLogger(EntityItemsReader.class);

    private final FileUploadsProperties fileUploadsProperties;

    private final ExcelFileDeserializer<EVM> deserializer;
    private final DataFileUploadService<F> fileUploadService;
    private final long fileId;

    private ListPartition<EVM> evmListPartition;

    public EntityItemsReader(
        final ExcelFileDeserializer<EVM> deserializer,
        final DataFileUploadService<F> fileUploadService,
        @Value("#{jobParameters['fileId']}") long fileId,
        final FileUploadsProperties fileUploadsProperties
    ) {
        this.deserializer = deserializer;
        this.fileUploadService = fileUploadService;
        this.fileId = fileId;
        this.fileUploadsProperties = fileUploadsProperties;
    }

    @PostConstruct
    private void resetIndex() {
        final List<EVM> unProcessedItems = new ArrayList<>();

        fileUploadService
            .findOne(fileId)
            .ifPresent(
                fileUpload -> {
                    unProcessedItems.addAll(deserializer.deserialize(fileUpload.getDataFile()));
                }
            );

        // Going for a big chunk due to expected file size
        evmListPartition = new ListPartition<>(fileUploadsProperties.getListSize(), unProcessedItems);

        log.info("List items deserialized : {}", unProcessedItems.size());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Every time this method is called, it will return a List of unprocessed items the size of which is dictated by the maximumPageSize;
     * <p>
     * Once the list of unprocessed items hits zero, the method call will return null;
     * </p>
     */
    @Override
    public List<EVM> read() throws Exception {
        List<EVM> forProcessing = evmListPartition.getPartition();

        log.info("Returning list of {} items", forProcessing.size());

        return forProcessing.size() == 0 ? null : forProcessing;
    }
}
