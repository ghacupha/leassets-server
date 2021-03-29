package io.github.leassets.internal.model.framework;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EntityRepository<D> extends JpaRepository<D, Long>, JpaSpecificationExecutor<D> {
}
