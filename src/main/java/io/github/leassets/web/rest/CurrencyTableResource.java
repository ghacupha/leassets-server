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

import io.github.leassets.repository.CurrencyTableRepository;
import io.github.leassets.service.CurrencyTableQueryService;
import io.github.leassets.service.CurrencyTableService;
import io.github.leassets.service.criteria.CurrencyTableCriteria;
import io.github.leassets.service.dto.CurrencyTableDTO;
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
 * REST controller for managing {@link io.github.leassets.domain.CurrencyTable}.
 */
@RestController
@RequestMapping("/api")
public class CurrencyTableResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyTableResource.class);

    private static final String ENTITY_NAME = "leassetsCurrencyTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrencyTableService currencyTableService;

    private final CurrencyTableRepository currencyTableRepository;

    private final CurrencyTableQueryService currencyTableQueryService;

    public CurrencyTableResource(
        CurrencyTableService currencyTableService,
        CurrencyTableRepository currencyTableRepository,
        CurrencyTableQueryService currencyTableQueryService
    ) {
        this.currencyTableService = currencyTableService;
        this.currencyTableRepository = currencyTableRepository;
        this.currencyTableQueryService = currencyTableQueryService;
    }

    /**
     * {@code POST  /currency-tables} : Create a new currencyTable.
     *
     * @param currencyTableDTO the currencyTableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currencyTableDTO, or with status {@code 400 (Bad Request)} if the currencyTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/currency-tables")
    public ResponseEntity<CurrencyTableDTO> createCurrencyTable(@Valid @RequestBody CurrencyTableDTO currencyTableDTO)
        throws URISyntaxException {
        log.debug("REST request to save CurrencyTable : {}", currencyTableDTO);
        if (currencyTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new currencyTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrencyTableDTO result = currencyTableService.save(currencyTableDTO);
        return ResponseEntity
            .created(new URI("/api/currency-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /currency-tables/:id} : Updates an existing currencyTable.
     *
     * @param id the id of the currencyTableDTO to save.
     * @param currencyTableDTO the currencyTableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencyTableDTO,
     * or with status {@code 400 (Bad Request)} if the currencyTableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currencyTableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/currency-tables/{id}")
    public ResponseEntity<CurrencyTableDTO> updateCurrencyTable(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CurrencyTableDTO currencyTableDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CurrencyTable : {}, {}", id, currencyTableDTO);
        if (currencyTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currencyTableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currencyTableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CurrencyTableDTO result = currencyTableService.save(currencyTableDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, currencyTableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /currency-tables/:id} : Partial updates given fields of an existing currencyTable, field will ignore if it is null
     *
     * @param id the id of the currencyTableDTO to save.
     * @param currencyTableDTO the currencyTableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencyTableDTO,
     * or with status {@code 400 (Bad Request)} if the currencyTableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the currencyTableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the currencyTableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/currency-tables/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CurrencyTableDTO> partialUpdateCurrencyTable(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CurrencyTableDTO currencyTableDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CurrencyTable partially : {}, {}", id, currencyTableDTO);
        if (currencyTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currencyTableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currencyTableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CurrencyTableDTO> result = currencyTableService.partialUpdate(currencyTableDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, currencyTableDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /currency-tables} : get all the currencyTables.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currencyTables in body.
     */
    @GetMapping("/currency-tables")
    public ResponseEntity<List<CurrencyTableDTO>> getAllCurrencyTables(CurrencyTableCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CurrencyTables by criteria: {}", criteria);
        Page<CurrencyTableDTO> page = currencyTableQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /currency-tables/count} : count all the currencyTables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/currency-tables/count")
    public ResponseEntity<Long> countCurrencyTables(CurrencyTableCriteria criteria) {
        log.debug("REST request to count CurrencyTables by criteria: {}", criteria);
        return ResponseEntity.ok().body(currencyTableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /currency-tables/:id} : get the "id" currencyTable.
     *
     * @param id the id of the currencyTableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currencyTableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/currency-tables/{id}")
    public ResponseEntity<CurrencyTableDTO> getCurrencyTable(@PathVariable Long id) {
        log.debug("REST request to get CurrencyTable : {}", id);
        Optional<CurrencyTableDTO> currencyTableDTO = currencyTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currencyTableDTO);
    }

    /**
     * {@code DELETE  /currency-tables/:id} : delete the "id" currencyTable.
     *
     * @param id the id of the currencyTableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/currency-tables/{id}")
    public ResponseEntity<Void> deleteCurrencyTable(@PathVariable Long id) {
        log.debug("REST request to delete CurrencyTable : {}", id);
        currencyTableService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/currency-tables?query=:query} : search for the currencyTable corresponding
     * to the query.
     *
     * @param query the query of the currencyTable search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/currency-tables")
    public ResponseEntity<List<CurrencyTableDTO>> searchCurrencyTables(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CurrencyTables for query {}", query);
        Page<CurrencyTableDTO> page = currencyTableService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
