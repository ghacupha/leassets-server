package io.github.leassets.internal.model;

import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.internal.model.framework.EntitySearchRepository;
import io.github.leassets.repository.search.FixedAssetAcquisitionSearchRepository;

/**
 * This interface extends the EntitySearchRepository interface with implementations from the fixed-asset-acquisition-search-repository
 */
public interface FixedAssetAcquisitionEntitySearchRepository extends EntitySearchRepository<FixedAssetAcquisition>, FixedAssetAcquisitionSearchRepository {
}
