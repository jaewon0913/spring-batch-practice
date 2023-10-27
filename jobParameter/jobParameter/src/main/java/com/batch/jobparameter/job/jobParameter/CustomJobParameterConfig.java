package com.batch.jobparameter.job.jobParameter;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class CustomJobParameterConfig {

    @Bean
    @JobScope
    public CustomJobParameter createJobParameter() {
        return new CustomJobParameter();
    }
}
