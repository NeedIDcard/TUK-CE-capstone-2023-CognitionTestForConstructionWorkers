package com.tukorea.cogTest.domain.enums;

public enum Risk {
    NORMAL("정상"),
    LOW_RISK("저위험군"),
    MIDEUM_RISK("중위험군"),
    HIGH_RISK("고위험군");

    public final String description;

    Risk(String description) {
        this.description = description;
    }
}
