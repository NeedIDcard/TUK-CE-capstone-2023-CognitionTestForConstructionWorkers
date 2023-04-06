package com.tukorea.cogTest.filter;

import com.tukorea.cogTest.domain.*;
import com.tukorea.cogTest.domain.enums.Role;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;


public class TestDataFilter implements Filter {
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }
}
