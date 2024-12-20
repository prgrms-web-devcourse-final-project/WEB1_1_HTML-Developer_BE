package com.backend.allreva.auth.security;

import com.backend.allreva.auth.application.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@Profile("!local")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * JWT 토큰을 검증하고 권한을 부여합니다.
     */
    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        // extract access token from cookie
        String accessToken = extractAccessToken(request);

        // ANONYMOUS 요청 처리
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Access Token X
        jwtService.validateToken(accessToken);

        // Access Token O
        String memberId = jwtService.extractMemberId(accessToken);

        setAuthenication(memberId, request);
        filterChain.doFilter(request, response);
    }

    /**
     * header에 있는 Access Token을 추출합니다.
     * @param request HTTP 요청
     * @return Access Token String 값 (없을 경우 null)
     */
    private String extractAccessToken(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("accessToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    /**
     * 사용자 인증을 수행하고 SecurityContextHolder에 저장합니다.
     * @param memberId 사용자 ID
     * @param request  HTTP 요청 객체
     */
    private void setAuthenication(final String memberId, final HttpServletRequest request) {
        // member db 확인
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        // add info (default - remote ip address, session id)
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Context Holder 저장
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
