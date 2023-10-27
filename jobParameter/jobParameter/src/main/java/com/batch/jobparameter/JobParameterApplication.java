package com.batch.jobparameter;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class JobParameterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobParameterApplication.class, args);
    }

}
