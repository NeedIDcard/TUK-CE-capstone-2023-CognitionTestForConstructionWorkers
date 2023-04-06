package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepository {
    @Override
    public List<Subject> findByField_id(Long fieldId) {
        return simpleSubjectRepository.findByField_Id(fieldId);
    }


    public final JpaSimpleSubjectRepository simpleSubjectRepository;
    @Override
    public Subject update(Long id, Subject updateArgument) throws RuntimeException{
        Subject foundedSubject = findById(id);
        return foundedSubject.update(updateArgument.toDTO());
    }

    @Override
    public Subject save(Subject subject) {
        subject.getField().appendWorkerNum();
        return simpleSubjectRepository.save(subject);
    }

    @Override
    public Subject findById(Long id) throws RuntimeException{
        return simpleSubjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such subject that has id " + id));
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException{
        Subject foundedSubject = simpleSubjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such subject that has id " + id));
        simpleSubjectRepository.delete(foundedSubject);
    }

    @Override
    public Subject findByUsername(String username) throws IllegalArgumentException{
        return simpleSubjectRepository.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("No such subject that has username "+username));
    }
}
