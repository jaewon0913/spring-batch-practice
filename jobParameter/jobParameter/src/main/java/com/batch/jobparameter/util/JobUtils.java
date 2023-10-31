package com.batch.jobparameter.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobUtils {

    private final JobLauncher jobLauncher;

    public void startBatchJob(Job jobName) throws Exception{

        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();

            jobParametersMap.put("param", new JobParameter(jobName.toString()));
            jobParametersMap.put("date", new JobParameter(LocalDate.now().toString()));

            JobExecution jobExecution = jobLauncher.run(jobName, new JobParameters(jobParametersMap));

            while(jobExecution.isRunning()){}

            log.info("ExitStatus : {} / Status : {}", jobExecution.getExitStatus(), jobExecution.getStatus());
        } catch (Exception e) {
            throw e;
        }
    }
}
