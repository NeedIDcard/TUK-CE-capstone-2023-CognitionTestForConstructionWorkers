package com.tukorea.cogTest.domain.test;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Tova extends Test {
    double responseTime;

}
