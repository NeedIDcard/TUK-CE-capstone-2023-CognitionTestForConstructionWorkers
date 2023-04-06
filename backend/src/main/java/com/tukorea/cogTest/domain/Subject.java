package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.enums.DetailedJob;
import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.dto.SubjectDTO;
import jakarta.persistence.*;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Entity(name = "subject")

@NoArgsConstructor
@Getter
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String username;
    String password;

    Role role;

    private Integer age;
    private Integer career;
    private String remarks;
    private Risk risk=Risk.NORMAL;

    @ManyToOne
    @JoinColumn(name="field_id")
    private Field field;

    @Builder
    public Subject(
            Long id,
            String name,
            String username,
            String password,
            Role role,
            Integer age,
            Integer career,
            String remarks,
            Risk risk,
            Field field) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.age = age;
        this.career = career;
        this.remarks = remarks;
        this.risk = risk;
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Subject item = (Subject) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    public Subject update(SubjectDTO subject){
        this.name = subject.getName();
        this.age = subject.getAge();
        this.career = subject.getCareer();
        this.field = subject.getField();
        this.remarks = subject.getRemarks();
        return this;
    }

    public void assignField(Field field){
        this.field = field;
    }

    public SubjectDTO toDTO(){
        return SubjectDTO.builder()
                .id(this.id)
                .age(this.age)
                .name(this.name)
                .career(this.career)
                .field(this.field)
                .remarks(this.remarks)
                .risk(this.risk)
                .build();
    }

    public void encodePassword(PasswordEncoder encoder){
        this.password = encoder.encode(password);
    }
}
