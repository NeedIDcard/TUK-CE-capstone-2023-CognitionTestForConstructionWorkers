package com.tukorea.cogTest.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FieldForm {
    String name;
    int numOfWorkers;

    @Builder
    public FieldForm(String name, int numOfWorkers) {
        this.name = name;
        this.numOfWorkers = numOfWorkers;
    }
}
