package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.enums.DetailedJob;
import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.enums.Risk;
import lombok.Builder;
import lombok.Data;

@Data
public class SubjectDTO {
     Long id;
     String name;
     Integer age;
     DetailedJob detailedJob;
     Integer career;
     String remarks;
     Risk risk;
     Field field;

    @Builder
    public SubjectDTO(Long id, String name, Integer age, DetailedJob detailedJob, Integer career, String remarks, Risk risk, Field field) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.detailedJob = detailedJob;
        this.career = career;
        this.remarks = remarks;
        this.risk = risk;
        this.field = field;
    }
}
