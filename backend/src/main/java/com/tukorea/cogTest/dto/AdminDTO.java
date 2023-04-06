package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDTO {
    Long id;
    String name;
    String username;
    String password;
    Role role;
    private String position;
    private Field field;
}
