package io.github.leassets.repository;

import io.github.leassets.domain.LeassetsFileType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LeassetsFileType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeassetsFileTypeRepository extends JpaRepository<LeassetsFileType, Long>, JpaSpecificationExecutor<LeassetsFileType> {}
