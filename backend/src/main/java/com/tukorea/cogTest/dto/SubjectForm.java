package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.enums.DetailedJob;
import com.tukorea.cogTest.domain.enums.Risk;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectForm {
    private String name;

    private String username;

    private String password;
    private Integer age;
    private DetailedJob detailedJob;
    private Integer career;
    private String remarks;
    private Risk risk;
    private FieldDTO fieldDTO;

    @Builder
    public SubjectForm(String name, Integer age, DetailedJob detailedJob, Integer career, String remarks, Risk risk, FieldDTO fieldDTO) {
        this.name = name;
        this.age = age;
        this.detailedJob = detailedJob;
        this.career = career;
        this.remarks = remarks;
        this.risk = risk;
        this.fieldDTO = fieldDTO;
    }
}
