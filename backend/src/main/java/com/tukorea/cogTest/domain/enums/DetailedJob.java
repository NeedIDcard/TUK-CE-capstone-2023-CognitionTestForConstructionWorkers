package com.tukorea.cogTest.domain.enums;

public enum DetailedJob {
    COMMON("일반"),
    SPECIAL("기능공");
    public final String  description;


    DetailedJob(String description) {
        this.description = description;
    }
}
