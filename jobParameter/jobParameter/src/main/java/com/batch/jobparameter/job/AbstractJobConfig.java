package com.batch.jobparameter.job;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;

public abstract class AbstractJobConfig {
    @Autowired protected JobBuilderFactory jobBuilderFactory;
    @Autowired protected StepBuilderFactory stepBuilderFactory;
    @Autowired protected EntityManagerFactory entityManagerFactory;
}
