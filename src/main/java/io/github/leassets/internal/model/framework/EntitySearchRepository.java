package io.github.leassets.internal.model.framework;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EntitySearchRepository<D> extends ElasticsearchRepository<D, Long> {
}
