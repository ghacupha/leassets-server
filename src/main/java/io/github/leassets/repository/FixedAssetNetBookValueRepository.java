package io.github.leassets.repository;

import io.github.leassets.domain.FixedAssetNetBookValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FixedAssetNetBookValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FixedAssetNetBookValueRepository extends JpaRepository<FixedAssetNetBookValue, Long>, JpaSpecificationExecutor<FixedAssetNetBookValue> {
}
