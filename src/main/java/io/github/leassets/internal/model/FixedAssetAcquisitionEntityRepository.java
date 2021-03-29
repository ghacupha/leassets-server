package io.github.leassets.internal.model;

import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.internal.model.framework.EntityRepository;
import io.github.leassets.repository.FixedAssetAcquisitionRepository;

/**
 * This interface extends EntityRepository interface with implementations from the domains fixed-asset-acquisition-repository
 */
public interface FixedAssetAcquisitionEntityRepository extends EntityRepository<FixedAssetAcquisition>, FixedAssetAcquisitionRepository {
}
