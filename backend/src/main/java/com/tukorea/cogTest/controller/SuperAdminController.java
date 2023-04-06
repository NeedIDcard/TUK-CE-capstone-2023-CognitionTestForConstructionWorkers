package com.tukorea.cogTest.controller;

import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.AdminForm;
import com.tukorea.cogTest.paging.Page;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/super")
public class SuperAdminController {

    private final AdminService adminService;


    @PostMapping("/admin")
    public ResponseEntity<Map<String, Object>> addAdmin(
            @ModelAttribute AdminForm adminForm
    ) {
        try {
            log.info("input adminForm : {}", adminForm);
            AdminDTO adminDTO = adminService.addAdmin(adminForm);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("admin", adminDTO);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Add common admin success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }
    @GetMapping("/admin/greetings")
    public ResponseEntity<Map<String, Object>> greetings(){
        Map<String, Object> body = new ConcurrentHashMap<>();
        body.put("result", "welcome");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        log.info("authentication : {}", authentication);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Map<String, Object>> getAdmin(
            @PathVariable Long id) {
        try {
            AdminDTO adminDTO = adminService.findById(id);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("admin", adminDTO);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get common admin success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    @GetMapping("/admins")
    public ResponseEntity<Map<String, Object>> getAdmins(
            @RequestParam int curPageNum,
            @RequestParam int contentsPerPage
    ) {
        try {
            List<AdminDTO> all = adminService.findAll();
            Page page = Page.getPage(curPageNum, contentsPerPage, "admin", all);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("page", page);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get common admin list success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<Map<String, Object>> updateAdmin(
            @PathVariable Long id,
            @ModelAttribute AdminForm adminForm) {
        AdminDTO adminDTO = AdminDTO.builder()
                .name(adminForm.getName())
                .position(adminForm.getPosition())
                .role(adminForm.getRole())
                .username(adminForm.getUsername())
                .password(adminForm.getPassword())
                .build();
        log.info("adminDTO : {}", adminDTO);
        try {
            AdminDTO updated = adminService.updateAdmin(id, adminDTO);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("admin", updated);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Update common admin success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Map<String, Object>> deleteAdmin(
            @PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Delete common admin success", null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("", e);
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            log.error("", e);
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }
}
