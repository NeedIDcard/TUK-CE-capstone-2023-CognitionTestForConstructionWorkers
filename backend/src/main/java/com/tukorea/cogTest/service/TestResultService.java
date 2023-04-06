package com.tukorea.cogTest.service;


import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.SubjectRepository;
import com.tukorea.cogTest.domain.TestResult;
import com.tukorea.cogTest.domain.TestResultRepository;
import com.tukorea.cogTest.dto.TestResultDTO;
import com.tukorea.cogTest.dto.TestResultForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TestResultService {

    private final TestResultRepository testResultRepository;
    private final SubjectRepository subjectRepository;

    /**
     * 테스트 결과를 저장한다.
     *
     * @param testResultForm 테스트 결과 폼 dto
     * @return TestResult 저장한 테스트 결과 객체
     * @throws IllegalArgumentException 테스트 결과 객체의 target이 유효하지 않을 때 발생
     */
    public TestResultDTO save(TestResultForm testResultForm, Long subjectId) throws IllegalArgumentException {
        Subject foundedSubject = subjectRepository.findById(subjectId);
        TestResult testResult = TestResult.builder()
                .target(foundedSubject)
                .date(testResultForm.getDate())
                .twoHandResult(testResultForm.getTwoHandResult())
                .craneResult(testResultForm.getCraneResult())
                .mazeResult(testResultForm.getMazeResult())
                .tovaResult(testResultForm.getTovaResult())
                .pvtResult(testResultForm.getPvtResult())
                .build();

        return testResultRepository.save(testResult).toDTO();
    }

    public List<TestResult> findBySubjectId(Long id){
        return testResultRepository.findByUserId(id);
    }


}
