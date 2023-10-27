package com.batch.jobparameter.scheduler;

import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchScheduler extends BatchAbstractScheduler{

    @Autowired
    @Qualifier("JobParameterBatchJob")
    Job JobParameterBatchJobConfig;
    @Autowired
    @Qualifier("MultiJobParameterBatchJob")
    Job MultiJobParameterBatchJobConfig;
    public void startScheduled(Long number){

        executorService.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {

                Job jobName = MultiJobParameterBatchJobConfig;
                if(number == 1L) {
                    jobName = JobParameterBatchJobConfig;
                }

                jobUtils.startBatchJob(jobName);
            }
        });
    }
}
