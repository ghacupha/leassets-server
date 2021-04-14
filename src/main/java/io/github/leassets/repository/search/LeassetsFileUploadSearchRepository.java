package io.github.leassets.repository.search;

import io.github.leassets.domain.LeassetsFileUpload;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link LeassetsFileUpload} entity.
 */
public interface LeassetsFileUploadSearchRepository extends ElasticsearchRepository<LeassetsFileUpload, Long> {
}
