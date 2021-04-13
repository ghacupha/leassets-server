package tech.leassets.repository;

import tech.leassets.domain.LeassetsMessageToken;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LeassetsMessageToken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeassetsMessageTokenRepository extends JpaRepository<LeassetsMessageToken, Long>, JpaSpecificationExecutor<LeassetsMessageToken> {
}
