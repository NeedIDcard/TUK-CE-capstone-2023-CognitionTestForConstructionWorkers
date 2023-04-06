package com.tukorea.cogTest.service;


import com.tukorea.cogTest.domain.*;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.dto.SubjectForm;
import com.tukorea.cogTest.dto.TestResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectService implements UserDetailsService{
    private final SubjectRepository subjectRepository;
    private final TestResultRepository testResultRepository;

    private final FieldRepository fieldRepository;
    /**
     * 피험자의 id를 받아서 피험자를 검색한 후 그 피험자의 모든 테스트 내용을 가져온다.
     * @param subjectId : 피험자의 id
     * @return List&lt;TestResult&gt; : 테스트 결과 객체 리스트
     * @throws RuntimeException : 피험자의 id에 해당하는 피험자를 찾지 못했을 경우 IllegalArgumentException을 throw한다.
     */
    public List<TestResultDTO> findTestResult(Long subjectId) throws RuntimeException{
        Subject foundedSubject = subjectRepository.findById(subjectId);
        List<TestResultDTO> result = new ArrayList<>();
        List<TestResult> byUserId = testResultRepository.findByUserId(foundedSubject.getId());
        for(TestResult testResult : byUserId){
            result.add(testResult.toDTO());
        }
        return result;
    }

    /**
     * 피험자의 id에 해당하는 피험자 객체를 반환한다.
     * @param subjectId : 피험자의 id
     * @return Subject : 피험자 객체
     * @throws RuntimeException : 피험자의 id에 해당하는 피험자를 찾지 못했을 경우 IllegalArgumentException을 throw한다.
     */
    public SubjectDTO findSubject(Long subjectId) throws RuntimeException{
        return subjectRepository.findById(subjectId).toDTO();
    }
    public List<SubjectDTO> findSubjectInField(Long fieldId){
        return subjectRepository.findByField_id(fieldId).stream().map(
                Subject::toDTO).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         try {
             Subject foundedSubject = subjectRepository.findByUsername(username);
             return User.withUsername(foundedSubject.getUsername())
                     .password(foundedSubject.getPassword())
                     .roles(foundedSubject.getRole().value)
                     .build();
         } catch (IllegalArgumentException e) {
             throw new UsernameNotFoundException(e.getMessage(), e);
         }
    }
    public SubjectDTO findByUsername(String username){
        Subject byUsername = subjectRepository.findByUsername(username);
        return byUsername.toDTO();
    }
    public SubjectDTO update(Long id, SubjectForm subjectDTO){
        Field foundedField = fieldRepository.findById(subjectDTO.getFieldDTO().getId());

        Subject subject = Subject.builder()
                .name(subjectDTO.getName())
                .age(subjectDTO.getAge())
                .field(foundedField)
                .risk(subjectDTO.getRisk())
                .remarks(subjectDTO.getRemarks())
                .career(subjectDTO.getCareer())
                .build();
        return subjectRepository.update(id, subject).toDTO();
    }

    /**
     * subjectId를 가지는 피험자의 모든 test result와 피험자를 삭제한다.
     * @param subjectId
     * @throws IllegalArgumentException : 피험자가 test result를 가지고 있지 않으면 발생한다.
     */
    public void delete(Long subjectId)throws IllegalArgumentException{
        List<TestResult> byUserId = testResultRepository.findByUserId(subjectId);
        if(byUserId!=null){
            testResultRepository.deleteAllBySubjectId(subjectId);
            subjectRepository.delete(subjectId);
        }
    }
}
