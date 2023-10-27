package com.batch.jobparameter.job.jobParameter;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class CustomJobParameter {

    @Value("#{jobParameters[param]}")
    private String param;
    private LocalDate date;

    @Value("#{jobParameters[date]}")
    public void setDate(String date) {
        if(!ObjectUtils.isEmpty(date)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.date = LocalDate.parse(date, formatter);
        }
    }
}
