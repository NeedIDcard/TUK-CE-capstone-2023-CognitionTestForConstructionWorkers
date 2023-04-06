package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSimpleFieldRepository extends JpaRepository<Field, Long> {

}
