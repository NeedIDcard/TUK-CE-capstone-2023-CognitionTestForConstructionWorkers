package com.tukorea.cogTest.domain.test;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Crane extends Test {
    double responseTime;

}
