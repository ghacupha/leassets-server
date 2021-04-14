package io.github.leassets.repository.search;

import io.github.leassets.domain.FixedAssetAcquisition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link FixedAssetAcquisition} entity.
 */
public interface FixedAssetAcquisitionSearchRepository extends ElasticsearchRepository<FixedAssetAcquisition, Long> {
}
