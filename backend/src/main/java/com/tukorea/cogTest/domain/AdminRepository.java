package com.tukorea.cogTest.domain;

import java.util.List;

public interface AdminRepository {
    Admin save(Admin admin);
    Admin findById(Long id);

    Admin findByUsername(String username);

    Admin update(Long id, Admin admin);
    void delete(Long id);

    List<Admin> findAll();

    Admin findByFieldId(Long id);
}
