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

import io.github.leassets.service.LeassetsMessageTokenService;
import io.github.leassets.web.rest.errors.BadRequestAlertException;
import io.github.leassets.service.dto.LeassetsMessageTokenDTO;
import io.github.leassets.service.dto.LeassetsMessageTokenCriteria;
import io.github.leassets.service.LeassetsMessageTokenQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.leassets.domain.LeassetsMessageToken}.
 */
@RestController
@RequestMapping("/api")
public class LeassetsMessageTokenResource {

    private final Logger log = LoggerFactory.getLogger(LeassetsMessageTokenResource.class);

    private static final String ENTITY_NAME = "leassetsLeassetsMessageToken";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeassetsMessageTokenService leassetsMessageTokenService;

    private final LeassetsMessageTokenQueryService leassetsMessageTokenQueryService;

    public LeassetsMessageTokenResource(LeassetsMessageTokenService leassetsMessageTokenService, LeassetsMessageTokenQueryService leassetsMessageTokenQueryService) {
        this.leassetsMessageTokenService = leassetsMessageTokenService;
        this.leassetsMessageTokenQueryService = leassetsMessageTokenQueryService;
    }

    /**
     * {@code POST  /leassets-message-tokens} : Create a new leassetsMessageToken.
     *
     * @param leassetsMessageTokenDTO the leassetsMessageTokenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leassetsMessageTokenDTO, or with status {@code 400 (Bad Request)} if the leassetsMessageToken has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leassets-message-tokens")
    public ResponseEntity<LeassetsMessageTokenDTO> createLeassetsMessageToken(@Valid @RequestBody LeassetsMessageTokenDTO leassetsMessageTokenDTO) throws URISyntaxException {
        log.debug("REST request to save LeassetsMessageToken : {}", leassetsMessageTokenDTO);
        if (leassetsMessageTokenDTO.getId() != null) {
            throw new BadRequestAlertException("A new leassetsMessageToken cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeassetsMessageTokenDTO result = leassetsMessageTokenService.save(leassetsMessageTokenDTO);
        return ResponseEntity.created(new URI("/api/leassets-message-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leassets-message-tokens} : Updates an existing leassetsMessageToken.
     *
     * @param leassetsMessageTokenDTO the leassetsMessageTokenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leassetsMessageTokenDTO,
     * or with status {@code 400 (Bad Request)} if the leassetsMessageTokenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leassetsMessageTokenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leassets-message-tokens")
    public ResponseEntity<LeassetsMessageTokenDTO> updateLeassetsMessageToken(@Valid @RequestBody LeassetsMessageTokenDTO leassetsMessageTokenDTO) throws URISyntaxException {
        log.debug("REST request to update LeassetsMessageToken : {}", leassetsMessageTokenDTO);
        if (leassetsMessageTokenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeassetsMessageTokenDTO result = leassetsMessageTokenService.save(leassetsMessageTokenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leassetsMessageTokenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /leassets-message-tokens} : get all the leassetsMessageTokens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leassetsMessageTokens in body.
     */
    @GetMapping("/leassets-message-tokens")
    public ResponseEntity<List<LeassetsMessageTokenDTO>> getAllLeassetsMessageTokens(LeassetsMessageTokenCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeassetsMessageTokens by criteria: {}", criteria);
        Page<LeassetsMessageTokenDTO> page = leassetsMessageTokenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leassets-message-tokens/count} : count all the leassetsMessageTokens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leassets-message-tokens/count")
    public ResponseEntity<Long> countLeassetsMessageTokens(LeassetsMessageTokenCriteria criteria) {
        log.debug("REST request to count LeassetsMessageTokens by criteria: {}", criteria);
        return ResponseEntity.ok().body(leassetsMessageTokenQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leassets-message-tokens/:id} : get the "id" leassetsMessageToken.
     *
     * @param id the id of the leassetsMessageTokenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leassetsMessageTokenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leassets-message-tokens/{id}")
    public ResponseEntity<LeassetsMessageTokenDTO> getLeassetsMessageToken(@PathVariable Long id) {
        log.debug("REST request to get LeassetsMessageToken : {}", id);
        Optional<LeassetsMessageTokenDTO> leassetsMessageTokenDTO = leassetsMessageTokenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leassetsMessageTokenDTO);
    }

    /**
     * {@code DELETE  /leassets-message-tokens/:id} : delete the "id" leassetsMessageToken.
     *
     * @param id the id of the leassetsMessageTokenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leassets-message-tokens/{id}")
    public ResponseEntity<Void> deleteLeassetsMessageToken(@PathVariable Long id) {
        log.debug("REST request to delete LeassetsMessageToken : {}", id);
        leassetsMessageTokenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/leassets-message-tokens?query=:query} : search for the leassetsMessageToken corresponding
     * to the query.
     *
     * @param query the query of the leassetsMessageToken search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/leassets-message-tokens")
    public ResponseEntity<List<LeassetsMessageTokenDTO>> searchLeassetsMessageTokens(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LeassetsMessageTokens for query {}", query);
        Page<LeassetsMessageTokenDTO> page = leassetsMessageTokenService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
