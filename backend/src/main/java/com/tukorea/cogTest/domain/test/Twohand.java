package com.tukorea.cogTest.domain.test;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Twohand extends Test{
    private Double totalMeanDuration;
    private Double totalMeanError;

    @Builder
    public Twohand(Double totalMeanDuration, Double totalMeanError) {
        this.totalMeanDuration = totalMeanDuration;
        this.totalMeanError = totalMeanError;
    }
}
