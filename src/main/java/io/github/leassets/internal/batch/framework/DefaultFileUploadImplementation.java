package io.github.leassets.internal.batch.framework;

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

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * This is an abstract implementation for the data-file-upload-service. The implementation
 * does not do much as most of the implementation detail is actually handled by the client. The
 * class provides uniformity in persistence procedures
 *
 * @param <F> File Upload Class
 */
public abstract class DefaultFileUploadImplementation<F> implements DataFileUploadService<F> {

    @Override
    public F save(F fileUploadDTO) {
        return getFileUploadService().save(fileUploadDTO);
    }

    @Override
    public Optional<F> partialUpdate(F fileUploadDTO) {
        return getFileUploadService().partialUpdate(fileUploadDTO);
    }

    @Override
    public Page<? extends HasDataFile> findAll(Pageable pageable) {
        return getFileUploadService().findAll(pageable);
    }

    @Override
    public Optional<? extends HasDataFile> findOne(Long id) {
        return getFileUploadService().findOne(id);
    }

    @Override
    public void delete(Long id) {
        getFileUploadService().delete(id);
    }

    @Override
    public Page<? extends HasDataFile> search(String query, Pageable pageable) {
        return getFileUploadService().search(query, pageable);
    }

    protected abstract DataFileUploadService<F> getFileUploadService();
}
