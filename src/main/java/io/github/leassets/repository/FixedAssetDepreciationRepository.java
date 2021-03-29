package io.github.leassets.repository;

import io.github.leassets.domain.FixedAssetDepreciation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FixedAssetDepreciation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FixedAssetDepreciationRepository
    extends JpaRepository<FixedAssetDepreciation, Long>, JpaSpecificationExecutor<FixedAssetDepreciation> {}
