package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaSimpleTestResultRepository extends JpaRepository<TestResult, Long> {
    void deleteByTarget_id(Long targetId);
    List<TestResult> findByTarget_Id(Long id);
    Optional<TestResult> findByTarget_idAndDate(Long userId, LocalDate date);
}
