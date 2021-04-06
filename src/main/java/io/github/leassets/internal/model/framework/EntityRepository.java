package io.github.leassets.internal.model.framework;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EntityRepository<D> {
    void indexAll(List<D> entities);

    List<D> saveAll(List<D> entities);
}
