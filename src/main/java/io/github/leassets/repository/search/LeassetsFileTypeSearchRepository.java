package io.github.leassets.repository.search;

import io.github.leassets.domain.LeassetsFileType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link LeassetsFileType} entity.
 */
public interface LeassetsFileTypeSearchRepository extends ElasticsearchRepository<LeassetsFileType, Long> {
}
