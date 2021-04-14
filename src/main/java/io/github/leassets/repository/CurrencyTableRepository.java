package io.github.leassets.repository;

import io.github.leassets.domain.CurrencyTable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CurrencyTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrencyTableRepository extends JpaRepository<CurrencyTable, Long>, JpaSpecificationExecutor<CurrencyTable> {
}
