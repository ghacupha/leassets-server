package io.github.leassets.internal.batch.framework;

import io.github.leassets.internal.batch.PersistenceJobListener;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
