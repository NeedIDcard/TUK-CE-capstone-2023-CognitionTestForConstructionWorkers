package com.tukorea.cogTest.security.filter;

import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.security.jwt.TokenUtil;
import com.tukorea.cogTest.service.AdminService;
import com.tukorea.cogTest.service.SubjectService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final String secret;
    private final AdminService adminService;
    private final SubjectService subjectService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("jwt filter 시작");
        // 로그인 페이지는 필요 없음.
        String requestURI = request.getRequestURI();
        String mainResource = requestURI.split("/")[1];
        if (requestURI.contains("login") || requestURI.contains("register")) {
            log.info("로그인,회원가입 페이지 진입");
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            // 토큰 헤더 없음.
            log.info("토큰 헤더 없음");
            filterChain.doFilter(request, response);
            return;
        }
        String[] tokenArray = authorizationHeader.split(" ");
        if (!tokenArray[0].equals("Bearer")) {
            // 인증 타입 다름
            log.info("인증 타입 다름");
            filterChain.doFilter(request, response);
            return;
        }
        if (tokenArray.length != 2) {
            // 토큰 온전하지 않음
            log.info("토큰 온전하지 않음");
            filterChain.doFilter(request, response);
            return;
        }

        // 맞는지 확인
        String jwtToken = tokenArray[1];
        Claims claims = TokenUtil.extractClaim(secret, jwtToken);
        Date expiration = claims.getExpiration();
        String id = (String) claims.get("userId");

        // 만료됨
        if (expiration.before(new Date(System.currentTimeMillis()))) {
            log.info("토큰 만료됨");
            filterChain.doFilter(request, response);
            return;
        }
        // 로그아웃 체크

        if (mainResource.equals("admin") || mainResource.equals("super")) {
            AdminDTO byId = adminService.findById(Long.valueOf(id));
            UserDetails user = adminService.loadUserByUsername(byId.getUsername());

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        } else if (mainResource.equals("subject")) {
            SubjectDTO byId = subjectService.findSubject(Long.valueOf(id));
            UserDetails user = subjectService.loadUserByUsername(byId.getUsername());

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), user.getAuthorities()
            );
            token.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request,response);
    }
}
