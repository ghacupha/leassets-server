package tech.leassets.repository.search;

import tech.leassets.domain.LeassetsMessageToken;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link LeassetsMessageToken} entity.
 */
public interface LeassetsMessageTokenSearchRepository extends ElasticsearchRepository<LeassetsMessageToken, Long> {
}
