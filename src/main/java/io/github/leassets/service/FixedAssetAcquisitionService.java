package io.github.leassets.service;

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

import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.leassets.domain.FixedAssetAcquisition}.
 */
public interface FixedAssetAcquisitionService {

    /**
     * Save a fixedAssetAcquisition.
     *
     * @param fixedAssetAcquisitionDTO the entity to save.
     * @return the persisted entity.
     */
    FixedAssetAcquisitionDTO save(FixedAssetAcquisitionDTO fixedAssetAcquisitionDTO);

    /**
     * Get all the fixedAssetAcquisitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FixedAssetAcquisitionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" fixedAssetAcquisition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FixedAssetAcquisitionDTO> findOne(Long id);

    /**
     * Delete the "id" fixedAssetAcquisition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the fixedAssetAcquisition corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FixedAssetAcquisitionDTO> search(String query, Pageable pageable);
}
