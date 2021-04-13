package tech.leassets.repository;

import tech.leassets.domain.LeassetsFileUpload;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LeassetsFileUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeassetsFileUploadRepository extends JpaRepository<LeassetsFileUpload, Long>, JpaSpecificationExecutor<LeassetsFileUpload> {
}
