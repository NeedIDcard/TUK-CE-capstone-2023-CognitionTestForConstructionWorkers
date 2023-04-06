package com.tukorea.cogTest.domain;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FieldTest {

    @Autowired
    private FieldRepository fieldRepository;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    void saveNFindTest(){
        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        Field savedField = fieldRepository.save(field);
        Field foundedField = fieldRepository.findById(savedField.getId());
        Assertions.assertThat(savedField).isEqualTo(foundedField);
    }

    @Test
    void updateTest(){
        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        Field savedField = fieldRepository.save(field);
        Field afterField = Field.builder()
                .name("updatedField")
                .numOfWorkers(1)
                .build();
        Field updatedField = fieldRepository.update(savedField.getId(), afterField);
        Assertions.assertThat(updatedField).isEqualTo(savedField);
        Assertions.assertThat(updatedField.getName()).isEqualTo(afterField.getName());
    }

    @Test
    void deleteTest(){
        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        Field savedField = fieldRepository.save(field);
        fieldRepository.delete(savedField.getId());
        Assertions.assertThatThrownBy(
                () -> fieldRepository.findById(savedField.getId())
        ).isInstanceOf(IllegalArgumentException.class);
    }
}