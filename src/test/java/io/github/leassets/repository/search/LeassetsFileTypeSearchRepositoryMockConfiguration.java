package io.github.leassets.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link LeassetsFileTypeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LeassetsFileTypeSearchRepositoryMockConfiguration {

    @MockBean
    private LeassetsFileTypeSearchRepository mockLeassetsFileTypeSearchRepository;

}
