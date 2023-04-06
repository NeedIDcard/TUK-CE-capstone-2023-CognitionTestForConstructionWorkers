package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.enums.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Data

public class AdminForm {
    String name;
    String username;
    String password;

    Long fieldId;
    Role role;
    String position;

    @Builder
    public AdminForm(String name, String username, String password, Long fieldId, Role role, String position) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.fieldId = fieldId;
        this.role = role;
        this.position = position;
    }
}
