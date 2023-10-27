package com.batch.jobparameter.job;

import com.batch.jobparameter.domain.Member;
import com.batch.jobparameter.job.jobParameter.CustomJobParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class MultiJobParameterBatchJobConfig extends AbstractJobConfig{

    private final CustomJobParameter customJobParameter;

    @Bean(name = "MultiJobParameterBatchJob")
    public Job job() {
        return jobBuilderFactory.get("MultiJobParameterBatchJob")
                .start(step())
                .build();
    }
    @Bean(name = "MultiJobParameterBatchStep")
    @JobScope
    public Step step() {

        return stepBuilderFactory.get("MultiJobParameterBatchStep")
                .<Member, Member>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean("MultiJobParameterBatchReader")
    @StepScope
    public JpaPagingItemReader<Member> reader() {

        log.info("ItemReader 에서는 데이터를 불러오는 로직 작성");
        log.info("Param : " + customJobParameter.getParam());
        log.info("Date: " + customJobParameter.getDate());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date", customJobParameter.getDate());

        return new JpaPagingItemReaderBuilder<Member>()
                .name("MultiJobParameterBatchReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(5)
                .queryString("SELECT m FROM Member m WHERE m.createDate = :date")
                .parameterValues(parameters)
                .build();
    }

    @Bean("MultiJobParameterBatchProcessor")
    @StepScope
    public ItemProcessor<Member, Member> processor() {
        log.info("ItemProcessor 에서는 데이터를 처리 하는 로직 작성");
        return item -> item;
    }

    @Bean("MultiJobParameterBatchWriter")
    @StepScope
    public ItemWriter<Member> writer() {
        log.info("ItemWriter 에서는 DB 저장과 같은 Transactional 한 로직 작성");

        return items -> {
            for(Member member: items) {
                log.info("Member Info : " + member.toString());
            }
        };
    }
}
