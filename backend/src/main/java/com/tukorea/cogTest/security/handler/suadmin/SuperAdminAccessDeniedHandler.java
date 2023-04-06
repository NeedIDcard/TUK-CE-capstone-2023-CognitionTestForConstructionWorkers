package com.tukorea.cogTest.security.handler.suadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.response.JsonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;
@Slf4j
@RequiredArgsConstructor
public class SuperAdminAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("super admin accessDeniedHandler working");
        if (accessDeniedException != null){
            log.info("super admin accessDeniedHandler working");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            JsonResponse body = new JsonResponse(accessDeniedException.getLocalizedMessage(), HttpStatus.UNAUTHORIZED.value(), null);
            String stringBody = objectMapper.writeValueAsString(body);
            PrintWriter writer = response.getWriter();
            writer.write(stringBody);
            writer.flush();
        }
    }
}
