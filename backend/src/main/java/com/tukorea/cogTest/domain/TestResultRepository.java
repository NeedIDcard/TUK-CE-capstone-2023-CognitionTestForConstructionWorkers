package com.tukorea.cogTest.domain;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TestResultRepository {

    TestResult save(TestResult item);

    TestResult update(Long id, TestResult item);

    TestResult findById(Long id);
    TestResult findByUserIdAndDate(Long userId, LocalDate date);

    List<TestResult> findByUserId(Long userId);
    void deleteAllBySubjectId(Long subjectId);
}
