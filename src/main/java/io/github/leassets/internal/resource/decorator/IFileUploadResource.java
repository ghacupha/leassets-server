package io.github.leassets.internal.resource.decorator;

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

import io.github.leassets.service.criteria.LeassetsFileUploadCriteria;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This interface provides the client alternative ways to implement already existing responses
 * <p/>
 * for file-uploads
 */
public interface IFileUploadResource {
    /**
     * {@code POST  /file-uploads} : Create a new fileUpload.
     *
     * @param fileUploadDTO the fileUploadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileUploadDTO, or with status {@code 400 (Bad Request)} if the fileUpload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<LeassetsFileUploadDTO> createFileUpload(@Valid @RequestBody LeassetsFileUploadDTO fileUploadDTO)
        throws URISyntaxException;

    /**
     * {@code PUT  /leassets-file-uploads/:id} : Updates an existing leassetsFileUpload.
     *
     * @param id the id of the leassetsFileUploadDTO to save.
     * @param fileUploadDTO the leassetsFileUploadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leassetsFileUploadDTO,
     * or with status {@code 400 (Bad Request)} if the leassetsFileUploadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leassetsFileUploadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<LeassetsFileUploadDTO> updateFileUpload(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeassetsFileUploadDTO fileUploadDTO
    ) throws URISyntaxException;

    /**
     * {@code GET  /file-uploads} : get all the fileUploads.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileUploads in body.
     */
    ResponseEntity<List<LeassetsFileUploadDTO>> getAllFileUploads(LeassetsFileUploadCriteria criteria, Pageable pageable);

    /**
     * {@code GET  /file-uploads/count} : count all the fileUploads.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    ResponseEntity<Long> countFileUploads(LeassetsFileUploadCriteria criteria);

    /**
     * {@code GET  /file-uploads/:id} : get the "id" fileUpload.
     *
     * @param id the id of the fileUploadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileUploadDTO, or with status {@code 404 (Not Found)}.
     */
    ResponseEntity<LeassetsFileUploadDTO> getFileUpload(@PathVariable Long id);

    /**
     * {@code DELETE  /file-uploads/:id} : delete the "id" fileUpload.
     *
     * @param id the id of the fileUploadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    ResponseEntity<Void> deleteFileUpload(@PathVariable Long id);
}
