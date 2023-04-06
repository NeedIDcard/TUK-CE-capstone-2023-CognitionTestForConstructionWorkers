package com.tukorea.cogTest.domain.enums;

public enum Role {
    USER("USER","일반 사용자"),
    ADMIN("ADMIN","일반 관리자"),
    SU_ADMIN("SU_ADMIN","슈퍼 관리자");

    public final String value;
    public final String description;

    Role(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
