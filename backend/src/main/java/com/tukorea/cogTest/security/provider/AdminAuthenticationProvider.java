package com.tukorea.cogTest.security.provider;

import com.tukorea.cogTest.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AdminAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private AdminService adminService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 클라이언트가 입력한 정보

        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        log.info("username={}", username);
        log.info("password={}", password);
        try {
            UserDetails foundedUser = adminService.loadUserByUsername(username);
            log.info("encoded={}", passwordEncoder.encode(foundedUser.getPassword()));

            log.info("foundedUser = {}", foundedUser);
            log.info("foundedUser.username={}, foundedUser.password={}", foundedUser.getUsername(), foundedUser.getPassword());
            log.info("foundedUser.authorities = {}", foundedUser.getAuthorities());
            log.info("encode={}", foundedUser.getPassword());
            // 인증 진행
            if (passwordEncoder.matches(password, foundedUser.getPassword())) {
                log.info("provider login success");
                return new UsernamePasswordAuthenticationToken(username, password, foundedUser.getAuthorities());
            } else {
                // 인증 실패
                throw new AuthenticationCredentialsNotFoundException("authentication failed");
            }
        } catch (RuntimeException e) {
            log.error("msg={}", e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
