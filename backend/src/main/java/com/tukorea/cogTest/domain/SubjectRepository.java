package com.tukorea.cogTest.domain;

import java.util.List;

public interface SubjectRepository {
     Subject save(Subject subject);
     Subject update(Long id, Subject subject);
     Subject findById(Long id);
     void delete(Long id);

     Subject findByUsername(String username);

     List<Subject> findByField_id(Long adminId);

}

