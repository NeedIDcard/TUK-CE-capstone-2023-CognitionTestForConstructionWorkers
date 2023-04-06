package com.tukorea.cogTest.repository;


import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.TestResult;
import com.tukorea.cogTest.domain.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class TestResultRepositoryImpl implements TestResultRepository {

    private final JpaSimpleTestResultRepository simpleTestResultRepository;
    private final JpaSimpleSubjectRepository simpleSubjectRepository;
    @Override
    public TestResult save(TestResult item) throws IllegalArgumentException{
        Long userId = item.getTarget().getId();
        if(userId == null){
            throw new NullPointerException("Subject id cannot be null.");
        }
        Optional<Subject> byId = simpleSubjectRepository.findById(userId);
        if(byId.isEmpty()){
            throw new IllegalArgumentException("No such subject that has id "+userId+".");
        }
        return simpleTestResultRepository.save(item);
    }

    @Override
    public TestResult update(Long id, TestResult item) throws RuntimeException{
        TestResult foundedResult = findById(id);
        return foundedResult.update(item);
    }

    @Override
    public TestResult findById(Long id) throws RuntimeException{
        return simpleTestResultRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such test result that has id " + id+"."));
    }

    @Override
    public TestResult findByUserIdAndDate(Long userId, LocalDate date) throws RuntimeException {
        return simpleTestResultRepository.findByTarget_idAndDate(userId, date)
                .orElseThrow(() -> new IllegalArgumentException("A subject that has id:" + userId + "'s date:" + date + " is not present."));
    }

    @Override
    public void deleteAllBySubjectId(Long subjectId) throws RuntimeException {
         simpleTestResultRepository.deleteByTarget_id(subjectId);
    }

    @Override
    public List<TestResult> findByUserId(Long userId) throws RuntimeException {
        return simpleTestResultRepository.findByTarget_Id(userId);
    }
}
