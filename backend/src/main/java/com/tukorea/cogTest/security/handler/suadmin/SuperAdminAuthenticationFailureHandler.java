package com.tukorea.cogTest.security.handler.suadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.response.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static com.tukorea.cogTest.response.ResponseUtil.*;

@RequiredArgsConstructor
public class SuperAdminAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        setRestResponseHeader(response);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResponseUtil.setJsonResponse(
                response,
                exception,
                objectMapper
        );
    }
}
