package io.github.leassets.repository.search;

import io.github.leassets.domain.FixedAssetDepreciation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FixedAssetDepreciation} entity.
 */
public interface FixedAssetDepreciationSearchRepository extends ElasticsearchRepository<FixedAssetDepreciation, Long> {}
