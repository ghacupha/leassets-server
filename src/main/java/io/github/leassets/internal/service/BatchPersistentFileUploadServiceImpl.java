package io.github.leassets.internal.service;

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

import io.github.leassets.internal.framework.batch.BatchPersistentFileUploadService;
import io.github.leassets.internal.framework.batch.HasDataFile;
import io.github.leassets.service.LeassetsFileUploadService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BatchPersistentFileUploadServiceImpl implements BatchPersistentFileUploadService {

    private final LeassetsFileUploadService fileUploadService;

    public BatchPersistentFileUploadServiceImpl(LeassetsFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Override
    public Optional<HasDataFile> findOne(long fileId) {

        AtomicReference<Optional<HasDataFile>> fileUpload = new AtomicReference<>();

        fileUploadService.findOne(fileId).ifPresent(optionalFile -> fileUpload.set(Optional.of(optionalFile)));

        return fileUpload.get();
    }
}
