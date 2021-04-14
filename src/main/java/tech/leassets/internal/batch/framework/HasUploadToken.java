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

/**
 * Represents and entity that contains a string upload-token
 */
public interface HasUploadToken<D> {
    /**
     * Takes a string token and applies the same to the client
     *
     * @param uploadToken
     */
    void setUploadToken(String uploadToken);

    String getUploadToken();
    //    /**
    //     * Simply returns the client's instance
    //     *
    //     * @return
    //     */
    //    D getChild();
}
