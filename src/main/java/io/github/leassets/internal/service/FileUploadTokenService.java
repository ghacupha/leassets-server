package io.github.leassets.internal.service;

/*-
 * Granular Summaries - Granular data analysis system
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
import java.util.List;
import java.util.Optional;

/**
 * This interface uses client's implementation to find list of entities whose string-token, message-token or
 * file-upload-token match the string value given in the argument.
 *
 * @param <T> Type of entity
 */
public interface FileUploadTokenService<T> {
    Optional<List<T>> findAllByUploadToken(String messageToken);
}