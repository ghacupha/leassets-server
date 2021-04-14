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

import tech.leassets.config.FileUploadsProperties;
import tech.leassets.internal.service.FileUploadTokenService;
import tech.leassets.service.dto.LeassetsFileUploadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * This is a short-term attempt at creating an abstract items-deletion-reader. The deletion
 * procedure is of a particular consequence that effects deletion of data originated from
 * a given data file,  given that the data has been marked with the same unique file-upload-token
 * of the file-upload entity's instance
 */
public class EntityItemsDeletionReader implements ItemReader<List<Long>> {

    private static final Logger log = LoggerFactory.getLogger(EntityItemsDeletionReader.class);
    private final long fileId;

    private final DataFileUploadService<LeassetsFileUploadDTO> fileUploadService;
    private final FileUploadsProperties fileUploadsProperties;
    private final FileUploadTokenService<? extends HasIndex> fileUploadTokenService;

    // TODO Initialize later, not in the constructor
    private ListPartition<Long> listPartition;

    public EntityItemsDeletionReader(
        final @Value("#{jobParameters['fileId']}") long fileId,
        final DataFileUploadService<LeassetsFileUploadDTO> fileUploadService,
        final FileUploadsProperties fileUploadsProperties,
        final FileUploadTokenService<? extends HasIndex> fileUploadTokenService
    ) {
        this.fileId = fileId;
        this.fileUploadService = fileUploadService;
        this.fileUploadsProperties = fileUploadsProperties;
        this.fileUploadTokenService = fileUploadTokenService;
    }

    @PostConstruct
    private void resetIndex() {
        final List<Long> unProcessedItems = new CopyOnWriteArrayList<>();

        // update list
        fileUploadService
            .findOne(fileId)
            .ifPresent(
                fileUpload -> {
                    String messageToken = fileUpload.getUploadToken();
                    fileUploadTokenService
                        .findAllByUploadToken(messageToken)
                        .ifPresent(
                            entities -> {
                                // TODO pass actual entities for deletion
                                unProcessedItems.addAll(entities.stream().map(HasIndex::getId).collect(Collectors.toList()));
                            }
                        );
                }
            );

        // Going for a big chunk due to expected file size
        listPartition = new ListPartition<>(fileUploadsProperties.getListSize(), unProcessedItems);

        log.info("List items realized : {}", unProcessedItems.size());
    }

    @Override
    public List<Long> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        List<Long> processingList = listPartition.getPartition();

        log.info("Returning list of {} items", processingList.size());

        return processingList.size() == 0 ? null : processingList;
    }
}
