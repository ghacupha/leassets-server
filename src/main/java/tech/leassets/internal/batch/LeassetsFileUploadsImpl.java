package tech.leassets.internal.batch;

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

import tech.leassets.internal.batch.framework.HasDataFile;
import tech.leassets.service.LeassetsFileUploadService;
import tech.leassets.service.dto.LeassetsFileUploadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("leassetsFileUploads")
public class LeassetsFileUploadsImpl implements LeassetsFileUploads<LeassetsFileUploadDTO> {

    private final LeassetsFileUploadService leassetsFileUploadService;

    public LeassetsFileUploadsImpl(LeassetsFileUploadService leassetsFileUploadService) {
        this.leassetsFileUploadService = leassetsFileUploadService;
    }

    @Override
    public LeassetsFileUploadDTO save(LeassetsFileUploadDTO fileUploadDTO) {
        return leassetsFileUploadService.save(fileUploadDTO);
    }

//    @Override
//    public Optional<LeassetsFileUploadDTO> partialUpdate(LeassetsFileUploadDTO fileUploadDTO) {
//        return leassetsFileUploadService.partialUpdate(fileUploadDTO);
//    }

    @Override
    public Page<? extends HasDataFile> findAll(Pageable pageable) {
        return leassetsFileUploadService.findAll(pageable);
    }

    @Override
    public Optional<? extends HasDataFile> findOne(Long id) {
        return leassetsFileUploadService.findOne(id);
    }

    @Override
    public void delete(Long id) {
        leassetsFileUploadService.delete(id);
    }

    @Override
    public Page<? extends HasDataFile> search(String query, Pageable pageable) {
        return leassetsFileUploadService.search(query, pageable);
    }
}
