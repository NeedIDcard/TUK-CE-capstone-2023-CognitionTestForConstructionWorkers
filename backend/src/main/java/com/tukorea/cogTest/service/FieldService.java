package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.*;
import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.FieldDTO;
import com.tukorea.cogTest.dto.FieldForm;
import com.tukorea.cogTest.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private AdminService adminService;

    @Autowired
    private SubjectService subjectService;


    public FieldDTO save(FieldForm fieldForm) throws RuntimeException{
        Field field = Field.builder()
                .name(fieldForm.getName())
                .numOfWorkers(fieldForm.getNumOfWorkers())
                .build();

        return fieldRepository.save(field).toDTO();
    }

    public FieldDTO findById(Long id) throws RuntimeException{
        return fieldRepository.findById(id).toDTO();
    }

    public FieldDTO update(Long id, FieldForm fieldForm) throws RuntimeException{
        Field field = Field.builder()
                .name(fieldForm.getName())
                .numOfWorkers(fieldForm.getNumOfWorkers())
                .build();
        return fieldRepository.update(id, field).toDTO();
    }

    public void delete(Long id) throws RuntimeException{

        // delete subject
        List<SubjectDTO> byFieldId = subjectService.findSubjectInField(id);
        for(SubjectDTO subjectDTO : byFieldId){
            subjectService.delete(subjectDTO.getId());
        }
        // delete admin
        AdminDTO byFieldIdAdmin = adminService.findByField_id(id);
        adminService.deleteAdmin(byFieldIdAdmin.getId());
        // delete field
        fieldRepository.delete(id);
    }


}
