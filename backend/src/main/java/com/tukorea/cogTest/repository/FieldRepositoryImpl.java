package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FieldRepositoryImpl implements FieldRepository {


    private final JpaSimpleFieldRepository simpleFieldRepository;
    @Override
    public Field save(Field field) throws IllegalArgumentException{
            return simpleFieldRepository.save(field);
    }

    @Override
    public Field findById(Long id) throws IllegalArgumentException  {
        return simpleFieldRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no such field." + id));
    }

    @Override
    public Field update(Long id, Field field) throws IllegalArgumentException {
        Field foundedField = findById(id);
        return foundedField.update(field);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        Field foundedField = findById(id);
        simpleFieldRepository.delete(foundedField);
    }
}
