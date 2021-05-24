package io.github.leassets.internal.resource;

import io.github.leassets.internal.resource.decorator.FixedAssetNetBookValueResourceDecorator;
import io.github.leassets.internal.resource.decorator.IFixedAssetNetBookValueResource;
import io.github.leassets.service.FixedAssetNetBookValueQueryService;
import io.github.leassets.service.dto.FixedAssetNetBookValueCriteria;
import io.github.leassets.service.dto.FixedAssetNetBookValueDTO;
import io.github.leassets.web.rest.FixedAssetNetBookValueResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This resource is created primarily for the purpose of defeating the 20-items
 * limit whose configuration we were unable to find
 */
@RestController
@RequestMapping("/api/app")
public class AppFixedAssetNetBookValueResource extends FixedAssetNetBookValueResourceDecorator implements IFixedAssetNetBookValueResource {

    private final FixedAssetNetBookValueQueryService fixedAssetNetBookValueQueryService;

    private static final Logger log = LoggerFactory.getLogger(AppFixedAssetNetBookValueResource.class);

    public AppFixedAssetNetBookValueResource(FixedAssetNetBookValueResource fixedAssetNetBookValueResource, FixedAssetNetBookValueQueryService fixedAssetNetBookValueQueryService) {
        super(fixedAssetNetBookValueResource);
        this.fixedAssetNetBookValueQueryService = fixedAssetNetBookValueQueryService;
    }

    /**
     * {@code GET  /fixed-asset-net-book-values} : get all the fixedAssetNetBookValues.
     *
     * Returns all fixed-asset-net-book-value items that fulfill the given criteria in a single page
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fixedAssetNetBookValues in body.
     */
    @GetMapping("/fixed-asset-net-book-values")
    public ResponseEntity<List<FixedAssetNetBookValueDTO>> getAllFixedAssetNetBookValues(FixedAssetNetBookValueCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FixedAssetNetBookValues by criteria: {}", criteria);

        // Configure pageable with total size consistent with a given criteria
        Pageable customPageable = PageRequest.of(0, Math.toIntExact(fixedAssetNetBookValueQueryService.countByCriteria(criteria)));

        return super.getAllFixedAssetNetBookValues(criteria, customPageable);
    }
}
