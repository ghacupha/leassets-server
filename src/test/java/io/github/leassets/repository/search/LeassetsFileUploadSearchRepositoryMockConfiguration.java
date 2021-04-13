package io.github.leassets.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link LeassetsFileUploadSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LeassetsFileUploadSearchRepositoryMockConfiguration {

    @MockBean
    private LeassetsFileUploadSearchRepository mockLeassetsFileUploadSearchRepository;
}
