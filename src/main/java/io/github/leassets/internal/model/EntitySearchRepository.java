package io.github.leassets.internal.model;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EntitySearchRepository<D> extends ElasticsearchRepository<D, Long> {
}
