package com.tukorea.cogTest.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.response.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.tukorea.cogTest.response.ResponseUtil.setJsonResponse;

@Slf4j
@RequiredArgsConstructor
public class AdminAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (accessDeniedException != null) {
            log.info("admin accessDeniedHandler working");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            ResponseUtil.setRestResponseHeader(response);
            setJsonResponse(response, accessDeniedException, objectMapper);
        }
    }


}
