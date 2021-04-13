package tech.leassets.repository;

import tech.leassets.domain.LeassetsFileType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LeassetsFileType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeassetsFileTypeRepository extends JpaRepository<LeassetsFileType, Long>, JpaSpecificationExecutor<LeassetsFileType> {
}
