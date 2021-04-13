package tech.leassets.web.rest;

import tech.leassets.service.FixedAssetAcquisitionService;
import tech.leassets.web.rest.errors.BadRequestAlertException;
import tech.leassets.service.dto.FixedAssetAcquisitionDTO;
import tech.leassets.service.dto.FixedAssetAcquisitionCriteria;
import tech.leassets.service.FixedAssetAcquisitionQueryService;

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
 * REST controller for managing {@link tech.leassets.domain.FixedAssetAcquisition}.
 */
@RestController
@RequestMapping("/api")
public class FixedAssetAcquisitionResource {

    private final Logger log = LoggerFactory.getLogger(FixedAssetAcquisitionResource.class);

    private static final String ENTITY_NAME = "fixedAssetAcquisition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FixedAssetAcquisitionService fixedAssetAcquisitionService;

    private final FixedAssetAcquisitionQueryService fixedAssetAcquisitionQueryService;

    public FixedAssetAcquisitionResource(FixedAssetAcquisitionService fixedAssetAcquisitionService, FixedAssetAcquisitionQueryService fixedAssetAcquisitionQueryService) {
        this.fixedAssetAcquisitionService = fixedAssetAcquisitionService;
        this.fixedAssetAcquisitionQueryService = fixedAssetAcquisitionQueryService;
    }

    /**
     * {@code POST  /fixed-asset-acquisitions} : Create a new fixedAssetAcquisition.
     *
     * @param fixedAssetAcquisitionDTO the fixedAssetAcquisitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fixedAssetAcquisitionDTO, or with status {@code 400 (Bad Request)} if the fixedAssetAcquisition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fixed-asset-acquisitions")
    public ResponseEntity<FixedAssetAcquisitionDTO> createFixedAssetAcquisition(@Valid @RequestBody FixedAssetAcquisitionDTO fixedAssetAcquisitionDTO) throws URISyntaxException {
        log.debug("REST request to save FixedAssetAcquisition : {}", fixedAssetAcquisitionDTO);
        if (fixedAssetAcquisitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new fixedAssetAcquisition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FixedAssetAcquisitionDTO result = fixedAssetAcquisitionService.save(fixedAssetAcquisitionDTO);
        return ResponseEntity.created(new URI("/api/fixed-asset-acquisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fixed-asset-acquisitions} : Updates an existing fixedAssetAcquisition.
     *
     * @param fixedAssetAcquisitionDTO the fixedAssetAcquisitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fixedAssetAcquisitionDTO,
     * or with status {@code 400 (Bad Request)} if the fixedAssetAcquisitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fixedAssetAcquisitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fixed-asset-acquisitions")
    public ResponseEntity<FixedAssetAcquisitionDTO> updateFixedAssetAcquisition(@Valid @RequestBody FixedAssetAcquisitionDTO fixedAssetAcquisitionDTO) throws URISyntaxException {
        log.debug("REST request to update FixedAssetAcquisition : {}", fixedAssetAcquisitionDTO);
        if (fixedAssetAcquisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FixedAssetAcquisitionDTO result = fixedAssetAcquisitionService.save(fixedAssetAcquisitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fixedAssetAcquisitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fixed-asset-acquisitions} : get all the fixedAssetAcquisitions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fixedAssetAcquisitions in body.
     */
    @GetMapping("/fixed-asset-acquisitions")
    public ResponseEntity<List<FixedAssetAcquisitionDTO>> getAllFixedAssetAcquisitions(FixedAssetAcquisitionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FixedAssetAcquisitions by criteria: {}", criteria);
        Page<FixedAssetAcquisitionDTO> page = fixedAssetAcquisitionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fixed-asset-acquisitions/count} : count all the fixedAssetAcquisitions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fixed-asset-acquisitions/count")
    public ResponseEntity<Long> countFixedAssetAcquisitions(FixedAssetAcquisitionCriteria criteria) {
        log.debug("REST request to count FixedAssetAcquisitions by criteria: {}", criteria);
        return ResponseEntity.ok().body(fixedAssetAcquisitionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fixed-asset-acquisitions/:id} : get the "id" fixedAssetAcquisition.
     *
     * @param id the id of the fixedAssetAcquisitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fixedAssetAcquisitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fixed-asset-acquisitions/{id}")
    public ResponseEntity<FixedAssetAcquisitionDTO> getFixedAssetAcquisition(@PathVariable Long id) {
        log.debug("REST request to get FixedAssetAcquisition : {}", id);
        Optional<FixedAssetAcquisitionDTO> fixedAssetAcquisitionDTO = fixedAssetAcquisitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fixedAssetAcquisitionDTO);
    }

    /**
     * {@code DELETE  /fixed-asset-acquisitions/:id} : delete the "id" fixedAssetAcquisition.
     *
     * @param id the id of the fixedAssetAcquisitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fixed-asset-acquisitions/{id}")
    public ResponseEntity<Void> deleteFixedAssetAcquisition(@PathVariable Long id) {
        log.debug("REST request to delete FixedAssetAcquisition : {}", id);
        fixedAssetAcquisitionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/fixed-asset-acquisitions?query=:query} : search for the fixedAssetAcquisition corresponding
     * to the query.
     *
     * @param query the query of the fixedAssetAcquisition search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fixed-asset-acquisitions")
    public ResponseEntity<List<FixedAssetAcquisitionDTO>> searchFixedAssetAcquisitions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FixedAssetAcquisitions for query {}", query);
        Page<FixedAssetAcquisitionDTO> page = fixedAssetAcquisitionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
