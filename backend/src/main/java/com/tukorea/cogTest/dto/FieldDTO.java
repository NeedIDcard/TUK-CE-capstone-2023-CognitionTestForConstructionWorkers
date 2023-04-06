package com.tukorea.cogTest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldDTO {
    Long id;
    private String name;

    private int numOfWorkers;
}
