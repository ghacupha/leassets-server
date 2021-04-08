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
package io.github.leassets.internal.batch.currencyTable;

import io.github.leassets.config.FileUploadsProperties;
import io.github.leassets.internal.batch.framework.ListPartition;
import io.github.leassets.internal.excel.ExcelFileDeserializer;
import io.github.leassets.internal.model.sampleDataModel.CurrencyTableEVM;
import io.github.leassets.service.LeassetsFileUploadService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

/**
 * This is sample configuration of the currency-table list-item-reader.
 * Take special note of how the listPartition is configured once the object is created at the
 * beginning of a job. This only works because the bean is configured with job-scope.
 */
@Scope("job")
public class CurrencyTableListItemReader implements ItemReader<List<CurrencyTableEVM>> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CurrencyTableListItemReader.class);
    private final FileUploadsProperties fileUploadsProperties;

    private final ExcelFileDeserializer<CurrencyTableEVM> deserializer;
    private final LeassetsFileUploadService fileUploadService;
    private long fileId;

    private ListPartition<CurrencyTableEVM> currencyTableEVMPartition;

    CurrencyTableListItemReader(
        final ExcelFileDeserializer<CurrencyTableEVM> deserializer,
        final LeassetsFileUploadService fileUploadService,
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
        final List<CurrencyTableEVM> unProcessedItems = deserializer.deserialize(
            fileUploadService
                .findOne(fileId)
                .orElseThrow(() -> new IllegalArgumentException(fileId + " was not found in the system"))
                .getDataFile()
        );

        currencyTableEVMPartition = new ListPartition<>(fileUploadsProperties.getListSize(), unProcessedItems);

        log.info("Currency table items deserialized : {}", unProcessedItems.size());
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
    public List<CurrencyTableEVM> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        List<CurrencyTableEVM> forProcessing = currencyTableEVMPartition.getPartition();

        log.info("Returning list of {} items", forProcessing.size());

        return forProcessing.size() == 0 ? null : forProcessing;
    }
}
