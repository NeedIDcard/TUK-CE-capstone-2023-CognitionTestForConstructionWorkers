package com.tukorea.cogTest.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.security.entrypoint.Http401ResponseEntryPoint;
import com.tukorea.cogTest.security.handler.*;
import com.tukorea.cogTest.security.handler.suadmin.SuperAdminAccessDeniedHandler;
import com.tukorea.cogTest.security.handler.suadmin.SuperAdminAuthenticationFailureHandler;
import com.tukorea.cogTest.security.handler.suadmin.SuperAdminAuthenticationSuccessHandler;
import com.tukorea.cogTest.security.handler.subject.SubjectAccessDeniedHandler;
import com.tukorea.cogTest.security.handler.subject.SubjectAuthenticationFailureHandler;
import com.tukorea.cogTest.security.handler.subject.SubjectAuthenticationSuccessHandler;
import com.tukorea.cogTest.security.provider.AdminAuthenticationProvider;
import com.tukorea.cogTest.security.provider.SubjectAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
@Slf4j

public class SecurityConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdminAuthenticationProvider adminAuthenticationProvider;

    @Value("${password.secret}")
    private String secret;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder(secret, 256, 30, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
    }

    @Bean
    SuperAdminAuthenticationSuccessHandler superAdminAuthenticationSuccessHandler(){
        return new SuperAdminAuthenticationSuccessHandler(objectMapper);
    }
    @Bean
    SuperAdminAuthenticationFailureHandler superAdminAuthenticationFailureHandler(){
        return new SuperAdminAuthenticationFailureHandler(objectMapper);
    }

    @Bean
    SuperAdminAccessDeniedHandler superAdminAccessDeniedHandler(){
        return new SuperAdminAccessDeniedHandler(objectMapper);
    }

    @Bean
    AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler(){
        return new AdminAuthenticationSuccessHandler(objectMapper);
    }

    @Bean
    AdminAuthenticationFailureHandler adminAuthenticationFailureHandler(){
        return new AdminAuthenticationFailureHandler(objectMapper);
    }

    @Bean
    AdminAccessDeniedHandler adminAccessDeniedHandler(){
        return new AdminAccessDeniedHandler(objectMapper);
    }

    @Autowired
    SubjectAuthenticationProvider subjectAuthenticationProvider;

    @Bean
    SubjectAuthenticationSuccessHandler subjectAuthenticationSuccessHandler(){
        return new SubjectAuthenticationSuccessHandler(objectMapper);
    }
    @Bean
    SubjectAuthenticationFailureHandler subjectAuthenticationFailureHandler(){
        return new SubjectAuthenticationFailureHandler(objectMapper);
    }
    @Bean
    SubjectAccessDeniedHandler subjectAccessDeniedHandler(){
        return new SubjectAccessDeniedHandler(objectMapper);
    }

    @Bean
    SecurityFilterChain suAdmin(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/super/admin/**", "/super/admins/**")
                .authenticationProvider(adminAuthenticationProvider)
                .authorizeHttpRequests()
                .anyRequest().hasRole(Role.SU_ADMIN.value)
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl("/super/admin/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(superAdminAuthenticationSuccessHandler())
                .failureHandler(superAdminAuthenticationFailureHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http401ResponseEntryPoint(objectMapper))
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .csrf()
                .disable();

        return http.build();
    }
    @Bean
    SecurityFilterChain admin(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .authenticationProvider(adminAuthenticationProvider)
                .authorizeHttpRequests()
                .anyRequest().hasRole(Role.ADMIN.value)
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl("/admin/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(adminAuthenticationSuccessHandler())
                .failureHandler(adminAuthenticationFailureHandler())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(adminAccessDeniedHandler())
                .authenticationEntryPoint(new Http401ResponseEntryPoint(objectMapper))
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .csrf()
                .disable();
        return http.build();
    }

    @Bean
    SecurityFilterChain site(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/site/**")
                .authorizeHttpRequests()
                .anyRequest().hasRole(Role.SU_ADMIN.value)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http401ResponseEntryPoint(objectMapper))
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .csrf()
                .disable();
        return http.build();
    }

    @Bean
    SecurityFilterChain subject(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/subject/**")
                .authenticationProvider(subjectAuthenticationProvider)
                .authorizeHttpRequests()
                .anyRequest().hasRole(Role.USER.value)
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl("/subject/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(subjectAuthenticationSuccessHandler())
                .failureHandler(subjectAuthenticationFailureHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http401ResponseEntryPoint(objectMapper))
                .accessDeniedHandler(subjectAccessDeniedHandler())
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .csrf()
                .disable();
        return http.build();
    }
    @Bean
    SecurityFilterChain h2console(HttpSecurity http) throws Exception {
        http.securityMatcher("/h2-console/**")
                .authorizeHttpRequests().anyRequest().permitAll()
                .and()
                .csrf()
                .ignoringRequestMatchers("/h2-console/**")
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        return http.build();
    }
}
