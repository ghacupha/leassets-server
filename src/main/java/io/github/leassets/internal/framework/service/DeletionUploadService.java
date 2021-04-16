package io.github.leassets.internal.framework.service;

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

import io.github.leassets.internal.framework.batch.HasDataFile;

import java.util.List;
import java.util.Optional;

/**
 * This interface is used in batch deletion job instances to access data about the
 * file-upload entity's instance that is being deleted. It only need to provide the file itself
 * based on the file-id as well as the file based on a string token
 *
 * The second method {@link DeletionUploadService::findAllByUploadToken()} uses the token to query the
 * repository find all instances of the DTO which have the same message-token. This method is specific to
 * the entity which is being configured for file-related batch deletion
 *
 * @param <DTO> This is the DTO containing the file-upload's instance message-token
 */
public interface DeletionUploadService<DTO> {

    /**
     * Returns an instance of the file-upload that contains the same file-id
     * @param fileId
     * @return
     */
    Optional<? extends HasDataFile> findOne(long fileId);

    /**
     * Return a list of persistent DTO entity instance whose file upload token is
     * equal to the stringToken provided in the args
     *
     * @param stringToken
     * @return
     */
    Optional<List<DTO>> findAllByUploadToken(String stringToken);
}
