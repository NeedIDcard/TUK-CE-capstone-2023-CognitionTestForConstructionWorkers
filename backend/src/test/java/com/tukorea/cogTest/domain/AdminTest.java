package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.enums.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AdminTest {

    @Autowired
    private AdminRepository adminRepository;
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    void saveNFindTest(){

        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        EntityManager em = emf.createEntityManager();
        em.persist(field);

        Admin admin = Admin.builder()
                .name("admin1")
                .position("position1")
                .field(field)
                .build();
        Admin savedAdmin = adminRepository.save(admin);
        Admin foundedAdmin = adminRepository.findById(savedAdmin.getId());
        Assertions.assertThat(savedAdmin).isEqualTo(foundedAdmin);
    }

    @Test
    void updateTest(){
        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        EntityManager em = emf.createEntityManager();
        em.persist(field);

        Admin admin = Admin.builder()
                .name("admin1")
                .position("position1")
                .field(field)
                .build();
        Admin savedAdmin = adminRepository.save(admin);
        Admin afterAdmin = Admin.builder()
                .name("updatedname")
                .position("updatedPosition")
                .field(field)
                .role(Role.ADMIN)
                .build();
        Admin updatedAdmin = adminRepository.update(savedAdmin.getId(), afterAdmin);
        Assertions.assertThat(updatedAdmin).isEqualTo(savedAdmin);
        Assertions.assertThat(updatedAdmin.getName()).isEqualTo(afterAdmin.getName());
    }
}