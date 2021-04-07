package io.github.leassets.internal.batch.framework;

import io.github.leassets.internal.batch.PersistenceJobListener;
import javax.sql.DataSource;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.XStreamExecutionContextStringSerializer;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Contains general configurations for batch related entities and objects
 *
 * This configuration also comes with the global activation of batch processes, as in
 * {@link EnableBatchProcessing}, annotation
 */
@Configuration
@EnableBatchProcessing
public class BatchConfigs {

    @Bean("persistenceJobListener")
    @JobScope
    public JobExecutionListener persistenceJobListener(
        @Value("#{jobParameters['fileId']}") long fileId,
        @Value("#{jobParameters['startUpTime']}") long startUpTime
    ) {
        return new PersistenceJobListener(fileId, startUpTime);
    }

    @Bean
    BatchConfigurer configurer(@Qualifier("dataSource") DataSource dataSource, PlatformTransactionManager transactionManager) {
        return new DefaultBatchConfigurer(dataSource) {
            @Override
            protected JobRepository createJobRepository() throws Exception {
                JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
                factory.setDataSource(dataSource);
                factory.setTransactionManager(transactionManager);
                factory.setSerializer(new XStreamExecutionContextStringSerializer());
                factory.afterPropertiesSet();
                return factory.getObject();
            }
        };
    }
}
