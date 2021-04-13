package tech.leassets.repository;

import tech.leassets.domain.FixedAssetAcquisition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FixedAssetAcquisition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FixedAssetAcquisitionRepository extends JpaRepository<FixedAssetAcquisition, Long>, JpaSpecificationExecutor<FixedAssetAcquisition> {
}