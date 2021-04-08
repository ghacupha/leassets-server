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

import static org.elasticsearch.index.query.QueryBuilders.*;

import io.github.leassets.domain.LeassetsFileType;
import io.github.leassets.repository.LeassetsFileTypeRepository;
import io.github.leassets.repository.search.LeassetsFileTypeSearchRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeassetsFileType}.
 */
@Service
@Transactional
public class LeassetsFileTypeService {

    private final Logger log = LoggerFactory.getLogger(LeassetsFileTypeService.class);

    private final LeassetsFileTypeRepository leassetsFileTypeRepository;

    private final LeassetsFileTypeSearchRepository leassetsFileTypeSearchRepository;

    public LeassetsFileTypeService(
        LeassetsFileTypeRepository leassetsFileTypeRepository,
        LeassetsFileTypeSearchRepository leassetsFileTypeSearchRepository
    ) {
        this.leassetsFileTypeRepository = leassetsFileTypeRepository;
        this.leassetsFileTypeSearchRepository = leassetsFileTypeSearchRepository;
    }

    /**
     * Save a leassetsFileType.
     *
     * @param leassetsFileType the entity to save.
     * @return the persisted entity.
     */
    public LeassetsFileType save(LeassetsFileType leassetsFileType) {
        log.debug("Request to save LeassetsFileType : {}", leassetsFileType);
        LeassetsFileType result = leassetsFileTypeRepository.save(leassetsFileType);
        leassetsFileTypeSearchRepository.save(result);
        return result;
    }

    /**
     * Partially update a leassetsFileType.
     *
     * @param leassetsFileType the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeassetsFileType> partialUpdate(LeassetsFileType leassetsFileType) {
        log.debug("Request to partially update LeassetsFileType : {}", leassetsFileType);

        return leassetsFileTypeRepository
            .findById(leassetsFileType.getId())
            .map(
                existingLeassetsFileType -> {
                    if (leassetsFileType.getLeassetsFileTypeName() != null) {
                        existingLeassetsFileType.setLeassetsFileTypeName(leassetsFileType.getLeassetsFileTypeName());
                    }
                    if (leassetsFileType.getLeassetsFileMediumType() != null) {
                        existingLeassetsFileType.setLeassetsFileMediumType(leassetsFileType.getLeassetsFileMediumType());
                    }
                    if (leassetsFileType.getDescription() != null) {
                        existingLeassetsFileType.setDescription(leassetsFileType.getDescription());
                    }
                    if (leassetsFileType.getFileTemplate() != null) {
                        existingLeassetsFileType.setFileTemplate(leassetsFileType.getFileTemplate());
                    }
                    if (leassetsFileType.getFileTemplateContentType() != null) {
                        existingLeassetsFileType.setFileTemplateContentType(leassetsFileType.getFileTemplateContentType());
                    }
                    if (leassetsFileType.getLeassetsfileType() != null) {
                        existingLeassetsFileType.setLeassetsfileType(leassetsFileType.getLeassetsfileType());
                    }

                    return existingLeassetsFileType;
                }
            )
            .map(leassetsFileTypeRepository::save)
            .map(
                savedLeassetsFileType -> {
                    leassetsFileTypeSearchRepository.save(savedLeassetsFileType);

                    return savedLeassetsFileType;
                }
            );
    }

    /**
     * Get all the leassetsFileTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeassetsFileType> findAll(Pageable pageable) {
        log.debug("Request to get all LeassetsFileTypes");
        return leassetsFileTypeRepository.findAll(pageable);
    }

    /**
     * Get one leassetsFileType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeassetsFileType> findOne(Long id) {
        log.debug("Request to get LeassetsFileType : {}", id);
        return leassetsFileTypeRepository.findById(id);
    }

    /**
     * Delete the leassetsFileType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeassetsFileType : {}", id);
        leassetsFileTypeRepository.deleteById(id);
        leassetsFileTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the leassetsFileType corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeassetsFileType> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LeassetsFileTypes for query {}", query);
        return leassetsFileTypeSearchRepository.search(queryStringQuery(query), pageable);
    }
}
