package com.tukorea.cogTest.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class CharsetFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("charset filter worked");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
