package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaSimpleSubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByField_Id(@NonNull Long id);
    Optional<Subject> findByUsername(String username);
}