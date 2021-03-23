package io.github.leassets.repository.search;

import io.github.leassets.domain.FixedAssetNetBookValue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FixedAssetNetBookValue} entity.
 */
public interface FixedAssetNetBookValueSearchRepository extends ElasticsearchRepository<FixedAssetNetBookValue, Long> {}
