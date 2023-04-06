package com.tukorea.cogTest.domain.test;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Pvt extends Test{
    private Double meanRT;
    private Double mean1divRT;

    public Pvt(Double meanRT, Double mean1divRT) {
        this.meanRT = meanRT;
        this.mean1divRT = mean1divRT;
    }

}
