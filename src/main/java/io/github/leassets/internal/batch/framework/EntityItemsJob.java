package io.github.leassets.internal.batch.framework;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.flow.FlowJob;


public class EntityItemsJob extends FlowJob implements Job {

    public EntityItemsJob(String name) {
        super(name);
    }
}
