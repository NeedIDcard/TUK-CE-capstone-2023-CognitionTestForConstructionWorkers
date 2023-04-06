package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.domain.test.Pvt;
import com.tukorea.cogTest.domain.test.Twohand;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
class TestResultTest {

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @PersistenceUnit
    private EntityManagerFactory emf;
    @Test
    void saveTest(){
        EntityManager em = emf.createEntityManager();
        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        Field savedField = fieldRepository.save(field);
        Subject subject = Subject.builder()
                .name("testname")
                .age(10)
                .career(10)
                .risk(Risk.HIGH_RISK)
                .remarks("")
                .field(savedField)
                .build();
        Subject savedSubject = subjectRepository.save(subject);
        TestResult result = TestResult.builder()
                .target(savedSubject)
                .build();
        TestResult savedResult = testResultRepository.save(result);
        TestResult foundedResult = testResultRepository.findById(savedResult.getId());
        Assertions.assertThat(savedResult).isEqualTo(foundedResult);
    }

    @Test
    void findByUserIdAndDate(){
        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        Field savedField = fieldRepository.save(field);
        Subject subject = Subject.builder()
                .name("testname")
                .age(10)
                .career(10)
                .risk(Risk.HIGH_RISK)
                .remarks("")
                .field(savedField)
                .build();
        Subject savedSubject = subjectRepository.save(subject);
        TestResult result = TestResult.builder()
                .target(savedSubject)
                .build();
        TestResult savedResult = testResultRepository.save(result);
        TestResult foundedTestResult = testResultRepository.findByUserIdAndDate(savedResult.getId(), LocalDate.now());
        Assertions.assertThat(foundedTestResult).isEqualTo(savedResult);
    }

    @Test
    void findByUserId(){
        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        Field savedField = fieldRepository.save(field);
        Subject subject1 = Subject.builder()
                .name("testname")
                .age(10)
                .career(10)
                .risk(Risk.HIGH_RISK)
                .remarks("")
                .field(savedField)
                .build();
        Subject savedSubject1 = subjectRepository.save(subject1);
        TestResult result1 = TestResult.builder()
                .target(savedSubject1)
                .build();
        testResultRepository.save(result1);
        TestResult result2 = TestResult.builder()
                .target(savedSubject1)
                .date(LocalDate.of(2023, 2, 8))
                .build();
        testResultRepository.save(result2);
        List<TestResult> foundedResult = testResultRepository.findByUserId(savedSubject1.getId());
        List<TestResult> expected = new ArrayList<>();
        expected.add(result1);
        expected.add(result2);
        Assertions.assertThat(foundedResult).isEqualTo(expected);

    }

    @Test
    void updateTest(){
        EntityManager em = emf.createEntityManager();
        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        em.persist(field);
        Subject subject = Subject.builder()
                .name("testname")
                .age(10)
                .career(10)
                .risk(Risk.HIGH_RISK)
                .remarks("")
                .field(field)
                .build();
        Subject savedSubject = subjectRepository.save(subject);
        TestResult result = TestResult.builder()
                .target(savedSubject)
                .twoHandResult(
                        Twohand.builder()
                                .totalMeanDuration(123.4)
                                .totalMeanError(2.34)
                                .build()
                )
                .build();
        TestResult savedResult = testResultRepository.save(result);
        TestResult afterResult = TestResult.builder()
                .target(savedSubject)
                .twoHandResult(Twohand.builder()
                        .totalMeanError(1.23)
                        .totalMeanDuration(1234.0)
                        .build())
                .pvtResult(new Pvt())
                .build();
        TestResult updatedResult = testResultRepository.update(savedResult.getId(), afterResult);
        Assertions.assertThat(savedResult).isEqualTo(updatedResult);
        Assertions.assertThat(updatedResult.getTwoHandResult().getTotalMeanDuration())
                .isEqualTo(afterResult.getTwoHandResult().getTotalMeanDuration());
    }
}