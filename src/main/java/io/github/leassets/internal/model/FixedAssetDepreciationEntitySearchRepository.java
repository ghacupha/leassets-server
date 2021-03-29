package io.github.leassets.internal.model;

import io.github.leassets.domain.FixedAssetDepreciation;
import io.github.leassets.internal.model.framework.EntitySearchRepository;
import io.github.leassets.repository.search.FixedAssetDepreciationSearchRepository;

/**
 * Implementation of the entity-search-repository extended from the domain fixed-asset-depreciation-search-repository
 */
public interface FixedAssetDepreciationEntitySearchRepository extends FixedAssetDepreciationSearchRepository, EntitySearchRepository<FixedAssetDepreciation> {
}
