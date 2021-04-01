package io.github.leassets.internal.batch.framework;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

/**
 * This is a general configuration of a single step job
 */
public class SingleStepEntityJob implements Job {

    private final Job job;

    public SingleStepEntityJob(String jobName, JobExecutionListener jobExecutionListener, Step step, JobBuilderFactory jobBuilderFactory) {
        this.job = jobBuilderFactory.get(jobName)
            .preventRestart()
            .listener(jobExecutionListener)
            .incrementer(new RunIdIncrementer())
            .flow(step)
            .end()
            .build();
    }

    @Override
    public String getName() {
        return this.job.getName();
    }

    @Override
    public boolean isRestartable() {
        return this.job.isRestartable();
    }

    @Override
    public void execute(JobExecution jobExecution) {
        this.job.execute(jobExecution);
    }

    @Override
    public JobParametersIncrementer getJobParametersIncrementer() {
        return this.job.getJobParametersIncrementer();
    }

    @Override
    public JobParametersValidator getJobParametersValidator() {
        return this.job.getJobParametersValidator();
    }
}
