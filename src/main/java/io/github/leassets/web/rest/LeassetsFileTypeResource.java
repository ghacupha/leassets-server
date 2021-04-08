package io.github.leassets.web.rest;

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
import io.github.leassets.service.LeassetsFileTypeQueryService;
import io.github.leassets.service.LeassetsFileTypeService;
import io.github.leassets.service.criteria.LeassetsFileTypeCriteria;
import io.github.leassets.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.leassets.domain.LeassetsFileType}.
 */
@RestController
@RequestMapping("/api")
public class LeassetsFileTypeResource {

    private final Logger log = LoggerFactory.getLogger(LeassetsFileTypeResource.class);

    private static final String ENTITY_NAME = "leassetsLeassetsFileType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeassetsFileTypeService leassetsFileTypeService;

    private final LeassetsFileTypeRepository leassetsFileTypeRepository;

    private final LeassetsFileTypeQueryService leassetsFileTypeQueryService;

    public LeassetsFileTypeResource(
        LeassetsFileTypeService leassetsFileTypeService,
        LeassetsFileTypeRepository leassetsFileTypeRepository,
        LeassetsFileTypeQueryService leassetsFileTypeQueryService
    ) {
        this.leassetsFileTypeService = leassetsFileTypeService;
        this.leassetsFileTypeRepository = leassetsFileTypeRepository;
        this.leassetsFileTypeQueryService = leassetsFileTypeQueryService;
    }

    /**
     * {@code POST  /leassets-file-types} : Create a new leassetsFileType.
     *
     * @param leassetsFileType the leassetsFileType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leassetsFileType, or with status {@code 400 (Bad Request)} if the leassetsFileType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leassets-file-types")
    public ResponseEntity<LeassetsFileType> createLeassetsFileType(@Valid @RequestBody LeassetsFileType leassetsFileType)
        throws URISyntaxException {
        log.debug("REST request to save LeassetsFileType : {}", leassetsFileType);
        if (leassetsFileType.getId() != null) {
            throw new BadRequestAlertException("A new leassetsFileType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeassetsFileType result = leassetsFileTypeService.save(leassetsFileType);
        return ResponseEntity
            .created(new URI("/api/leassets-file-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leassets-file-types/:id} : Updates an existing leassetsFileType.
     *
     * @param id the id of the leassetsFileType to save.
     * @param leassetsFileType the leassetsFileType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leassetsFileType,
     * or with status {@code 400 (Bad Request)} if the leassetsFileType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leassetsFileType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leassets-file-types/{id}")
    public ResponseEntity<LeassetsFileType> updateLeassetsFileType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeassetsFileType leassetsFileType
    ) throws URISyntaxException {
        log.debug("REST request to update LeassetsFileType : {}, {}", id, leassetsFileType);
        if (leassetsFileType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leassetsFileType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leassetsFileTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeassetsFileType result = leassetsFileTypeService.save(leassetsFileType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leassetsFileType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leassets-file-types/:id} : Partial updates given fields of an existing leassetsFileType, field will ignore if it is null
     *
     * @param id the id of the leassetsFileType to save.
     * @param leassetsFileType the leassetsFileType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leassetsFileType,
     * or with status {@code 400 (Bad Request)} if the leassetsFileType is not valid,
     * or with status {@code 404 (Not Found)} if the leassetsFileType is not found,
     * or with status {@code 500 (Internal Server Error)} if the leassetsFileType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leassets-file-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LeassetsFileType> partialUpdateLeassetsFileType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeassetsFileType leassetsFileType
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeassetsFileType partially : {}, {}", id, leassetsFileType);
        if (leassetsFileType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leassetsFileType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leassetsFileTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeassetsFileType> result = leassetsFileTypeService.partialUpdate(leassetsFileType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leassetsFileType.getId().toString())
        );
    }

    /**
     * {@code GET  /leassets-file-types} : get all the leassetsFileTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leassetsFileTypes in body.
     */
    @GetMapping("/leassets-file-types")
    public ResponseEntity<List<LeassetsFileType>> getAllLeassetsFileTypes(LeassetsFileTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeassetsFileTypes by criteria: {}", criteria);
        Page<LeassetsFileType> page = leassetsFileTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leassets-file-types/count} : count all the leassetsFileTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leassets-file-types/count")
    public ResponseEntity<Long> countLeassetsFileTypes(LeassetsFileTypeCriteria criteria) {
        log.debug("REST request to count LeassetsFileTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(leassetsFileTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leassets-file-types/:id} : get the "id" leassetsFileType.
     *
     * @param id the id of the leassetsFileType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leassetsFileType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leassets-file-types/{id}")
    public ResponseEntity<LeassetsFileType> getLeassetsFileType(@PathVariable Long id) {
        log.debug("REST request to get LeassetsFileType : {}", id);
        Optional<LeassetsFileType> leassetsFileType = leassetsFileTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leassetsFileType);
    }

    /**
     * {@code DELETE  /leassets-file-types/:id} : delete the "id" leassetsFileType.
     *
     * @param id the id of the leassetsFileType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leassets-file-types/{id}")
    public ResponseEntity<Void> deleteLeassetsFileType(@PathVariable Long id) {
        log.debug("REST request to delete LeassetsFileType : {}", id);
        leassetsFileTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/leassets-file-types?query=:query} : search for the leassetsFileType corresponding
     * to the query.
     *
     * @param query the query of the leassetsFileType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/leassets-file-types")
    public ResponseEntity<List<LeassetsFileType>> searchLeassetsFileTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LeassetsFileTypes for query {}", query);
        Page<LeassetsFileType> page = leassetsFileTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
