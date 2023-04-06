package com.tukorea.cogTest.security.provider;

import com.tukorea.cogTest.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SubjectAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        try {
            UserDetails foundedSubject = subjectService.loadUserByUsername(username);
            if (passwordEncoder.matches(password, foundedSubject.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, foundedSubject.getAuthorities());
            } else {
                throw new AuthenticationCredentialsNotFoundException("authentication failed");
            }
        }catch (UsernameNotFoundException e){
            throw new AuthenticationCredentialsNotFoundException("authentication failed", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
