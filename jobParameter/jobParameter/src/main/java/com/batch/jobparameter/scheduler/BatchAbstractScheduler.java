package com.batch.jobparameter.scheduler;

import com.batch.jobparameter.util.JobUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BatchAbstractScheduler {

    @Autowired protected JobUtils jobUtils;

    protected static final ExecutorService executorService = Executors.newFixedThreadPool(10);
}
