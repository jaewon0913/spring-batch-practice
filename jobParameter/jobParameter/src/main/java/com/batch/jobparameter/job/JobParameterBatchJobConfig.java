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
public class JobParameterBatchJobConfig extends AbstractJobConfig{
    private final CustomJobParameter jobParameter;

    // CustomJobParameterConfig 적용 후
//    @Bean("JobParameter")
//    @JobScope
//    public CustomJobParameter createJobParameter() {
//        return new CustomJobParameter();
//    }

    @Bean(name = "JobParameterBatchJob")
    public Job job() {
        return jobBuilderFactory.get("JobParameterBatchJob")
                .start(step())
                .build();
    }
    // CustomJobParameter 적용 후
    @Bean(name = "JobParameterBatchStep")
    @JobScope
    public Step step() {

        return stepBuilderFactory.get("JobParameterBatchStep")
                .<Member, Member>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean(name = "JobParameterBatchReader")
    @StepScope
    public JpaPagingItemReader<Member> reader() {

        log.info("ItemReader 에서는 데이터를 불러오는 로직 작성");
        log.info("Param : " + jobParameter.getParam());
        log.info("Date: " + jobParameter.getDate());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date", jobParameter.getDate());

        return new JpaPagingItemReaderBuilder<Member>()
                .name("JobParameterBatchReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(5)
                .queryString("SELECT m FROM Member m WHERE m.createDate = :date")
                .parameterValues(parameters)
                .build();
    }

    @Bean(name = "JobParameterBatchProcessor")
    @StepScope
    public ItemProcessor<Member, Member> processor() {
        log.info("ItemProcessor 에서는 데이터를 처리 하는 로직 작성");
        return item -> item;
    }

    @Bean(name = "JobParameterBatchWriter")
    @StepScope
    public ItemWriter<Member> writer() {
        log.info("ItemWriter 에서는 DB 저장과 같은 Transactional 한 로직 작성");

        return items -> {
            for(Member member: items) {
                log.info("Member Info : " + member.toString());
            }
        };
    }

    // CustomJobParameter 적용 전
//    @Bean(name = "jobParameterTestStep")
//    @JobScope
//    public Step simpleStep1() {
//
//        return stepBuilderFactory.get("simpleStep1")
//                .<Member, Member>chunk(5)
//                .reader(reader(null, null)) //  (1)
//                .processor(processor(null))
//                .writer(writer())
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public JpaPagingItemReader<Member> reader(
//            @Value("#{jobParameters[param]}") String param,
//            @Value("#{jobParameters[date]}") String date) { //  (2)
//
//        log.info("ItemReader 에서는 데이터를 불러오는 로직 작성");
//        log.info("Param : " + param);
//        log.info("Date: " + date);
//
//        Map<String, Object> parameters = new HashMap<>();
//        //parameters.put("date", date);   //  (3-1) Error 발생 Type Error
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        parameters.put("date", LocalDate.parse(date, formatter)); // (3-2) 정상 조회
//
//        return new JpaPagingItemReaderBuilder<Member>()
//                .name("JobParameterTestReader")
//                .entityManagerFactory(entityManagerFactory)
//                .pageSize(5)
//                .queryString("SELECT m FROM Member m WHERE m.createDate = :date")
//                .parameterValues(parameters)
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public ItemProcessor<Member, Member> processor(@Value("#{jobParameters[param]}") String param) {
//        log.info("ItemProcessor 에서는 데이터를 처리 하는 로직 작성");
//        return item -> item;
//    }
//
//    @Bean
//    public ItemWriter<Member> writer() {
//        log.info("ItemWriter 에서는 DB 저장과 같은 Transactional 한 로직 작성");
//
//        return items -> {
//            for(Member member: items) {
//                log.info("Member Info : " + member.toString());
//            }
//        };
//    }
}
