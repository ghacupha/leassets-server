package io.github.leassets.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import io.github.leassets.repository.LeassetsFileUploadRepository;
import io.github.leassets.service.LeassetsFileUploadQueryService;
import io.github.leassets.service.LeassetsFileUploadService;
import io.github.leassets.service.criteria.LeassetsFileUploadCriteria;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
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
 * REST controller for managing {@link io.github.leassets.domain.LeassetsFileUpload}.
 */
@RestController
@RequestMapping("/api")
public class LeassetsFileUploadResource {

    private final Logger log = LoggerFactory.getLogger(LeassetsFileUploadResource.class);

    private static final String ENTITY_NAME = "leassetsLeassetsFileUpload";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeassetsFileUploadService leassetsFileUploadService;

    private final LeassetsFileUploadRepository leassetsFileUploadRepository;

    private final LeassetsFileUploadQueryService leassetsFileUploadQueryService;

    public LeassetsFileUploadResource(
        LeassetsFileUploadService leassetsFileUploadService,
        LeassetsFileUploadRepository leassetsFileUploadRepository,
        LeassetsFileUploadQueryService leassetsFileUploadQueryService
    ) {
        this.leassetsFileUploadService = leassetsFileUploadService;
        this.leassetsFileUploadRepository = leassetsFileUploadRepository;
        this.leassetsFileUploadQueryService = leassetsFileUploadQueryService;
    }

    /**
     * {@code POST  /leassets-file-uploads} : Create a new leassetsFileUpload.
     *
     * @param leassetsFileUploadDTO the leassetsFileUploadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leassetsFileUploadDTO, or with status {@code 400 (Bad Request)} if the leassetsFileUpload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leassets-file-uploads")
    public ResponseEntity<LeassetsFileUploadDTO> createLeassetsFileUpload(@Valid @RequestBody LeassetsFileUploadDTO leassetsFileUploadDTO)
        throws URISyntaxException {
        log.debug("REST request to save LeassetsFileUpload : {}", leassetsFileUploadDTO);
        if (leassetsFileUploadDTO.getId() != null) {
            throw new BadRequestAlertException("A new leassetsFileUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeassetsFileUploadDTO result = leassetsFileUploadService.save(leassetsFileUploadDTO);
        return ResponseEntity
            .created(new URI("/api/leassets-file-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leassets-file-uploads/:id} : Updates an existing leassetsFileUpload.
     *
     * @param id the id of the leassetsFileUploadDTO to save.
     * @param leassetsFileUploadDTO the leassetsFileUploadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leassetsFileUploadDTO,
     * or with status {@code 400 (Bad Request)} if the leassetsFileUploadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leassetsFileUploadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leassets-file-uploads/{id}")
    public ResponseEntity<LeassetsFileUploadDTO> updateLeassetsFileUpload(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeassetsFileUploadDTO leassetsFileUploadDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LeassetsFileUpload : {}, {}", id, leassetsFileUploadDTO);
        if (leassetsFileUploadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leassetsFileUploadDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leassetsFileUploadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeassetsFileUploadDTO result = leassetsFileUploadService.save(leassetsFileUploadDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leassetsFileUploadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leassets-file-uploads/:id} : Partial updates given fields of an existing leassetsFileUpload, field will ignore if it is null
     *
     * @param id the id of the leassetsFileUploadDTO to save.
     * @param leassetsFileUploadDTO the leassetsFileUploadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leassetsFileUploadDTO,
     * or with status {@code 400 (Bad Request)} if the leassetsFileUploadDTO is not valid,
     * or with status {@code 404 (Not Found)} if the leassetsFileUploadDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the leassetsFileUploadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leassets-file-uploads/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LeassetsFileUploadDTO> partialUpdateLeassetsFileUpload(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeassetsFileUploadDTO leassetsFileUploadDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeassetsFileUpload partially : {}, {}", id, leassetsFileUploadDTO);
        if (leassetsFileUploadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leassetsFileUploadDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leassetsFileUploadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeassetsFileUploadDTO> result = leassetsFileUploadService.partialUpdate(leassetsFileUploadDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leassetsFileUploadDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /leassets-file-uploads} : get all the leassetsFileUploads.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leassetsFileUploads in body.
     */
    @GetMapping("/leassets-file-uploads")
    public ResponseEntity<List<LeassetsFileUploadDTO>> getAllLeassetsFileUploads(LeassetsFileUploadCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeassetsFileUploads by criteria: {}", criteria);
        Page<LeassetsFileUploadDTO> page = leassetsFileUploadQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leassets-file-uploads/count} : count all the leassetsFileUploads.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leassets-file-uploads/count")
    public ResponseEntity<Long> countLeassetsFileUploads(LeassetsFileUploadCriteria criteria) {
        log.debug("REST request to count LeassetsFileUploads by criteria: {}", criteria);
        return ResponseEntity.ok().body(leassetsFileUploadQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leassets-file-uploads/:id} : get the "id" leassetsFileUpload.
     *
     * @param id the id of the leassetsFileUploadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leassetsFileUploadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leassets-file-uploads/{id}")
    public ResponseEntity<LeassetsFileUploadDTO> getLeassetsFileUpload(@PathVariable Long id) {
        log.debug("REST request to get LeassetsFileUpload : {}", id);
        Optional<LeassetsFileUploadDTO> leassetsFileUploadDTO = leassetsFileUploadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leassetsFileUploadDTO);
    }

    /**
     * {@code DELETE  /leassets-file-uploads/:id} : delete the "id" leassetsFileUpload.
     *
     * @param id the id of the leassetsFileUploadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leassets-file-uploads/{id}")
    public ResponseEntity<Void> deleteLeassetsFileUpload(@PathVariable Long id) {
        log.debug("REST request to delete LeassetsFileUpload : {}", id);
        leassetsFileUploadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/leassets-file-uploads?query=:query} : search for the leassetsFileUpload corresponding
     * to the query.
     *
     * @param query the query of the leassetsFileUpload search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/leassets-file-uploads")
    public ResponseEntity<List<LeassetsFileUploadDTO>> searchLeassetsFileUploads(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LeassetsFileUploads for query {}", query);
        Page<LeassetsFileUploadDTO> page = leassetsFileUploadService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
