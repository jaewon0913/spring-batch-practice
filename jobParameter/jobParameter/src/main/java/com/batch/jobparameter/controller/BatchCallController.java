package com.batch.jobparameter.controller;

import com.batch.jobparameter.scheduler.BatchScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BatchCallController {
    private final BatchScheduler batchScheduler;

    @GetMapping("/test/{number}")
    public void test(@PathVariable Long number){
        batchScheduler.startScheduled(number);
    }
}
