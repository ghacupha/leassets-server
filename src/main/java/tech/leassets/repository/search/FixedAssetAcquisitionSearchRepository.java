package tech.leassets.repository.search;

import tech.leassets.domain.FixedAssetAcquisition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link FixedAssetAcquisition} entity.
 */
public interface FixedAssetAcquisitionSearchRepository extends ElasticsearchRepository<FixedAssetAcquisition, Long> {
}
