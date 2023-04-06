package com.tukorea.cogTest.domain.test;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Embeddable
public class Test {

    @Id
    @GeneratedValue
    private Long id;

    public Test() {
    }
}
