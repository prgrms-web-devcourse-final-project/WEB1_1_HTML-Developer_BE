package com.backend.allreva.auth.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.allreva.auth.exception.code.JwtTokenNotFoundException;
import com.backend.allreva.auth.util.JwtParser;
import com.backend.allreva.auth.util.JwtValidator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<RequestMatcher> EXCLUDED_URL_MATCHERS = List.of(
            new AntPathRequestMatcher("/h2-console/**"),
            // OAuth2 관련 URL
            new AntPathRequestMatcher("/api/v1/oauth2/login/**"),
            new AntPathRequestMatcher("/login/oauth2/**"));

    private final JwtParser jwtParser;
    private final JwtValidator jwtValidator;
    private final UserDetailsService userDetailsService;

    /**
     * JWT 토큰을 검증하는 메서드
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // JWT 인가 과정 필요없는 URL 제외
        if (isExcluded(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // request로부터 token 받기
        String refreshToken = jwtParser.getRefreshToken(request);
        String accessToken = jwtParser.getAccessToken(request);
        if (accessToken == null && refreshToken == null) {
            throw new JwtTokenNotFoundException();
        }

        // token 검증 수행
        if (accessToken != null) {
            jwtValidator.validateToken(accessToken);
        }

        if (accessToken == null && refreshToken != null) {
            // TODO: refreshToken validation 적용
        }

        // 토큰으로부터 member id 추출
        String memberId = jwtParser.extractMemberId(accessToken);

        // Authentication Security Holder에 저장
        setAuthenication(memberId, request);

        filterChain.doFilter(request, response);
    }

    private boolean isExcluded(HttpServletRequest request) {
        return EXCLUDED_URL_MATCHERS.stream()
                .anyMatch(matcher -> matcher.matches(request));
    }

    private void setAuthenication(String email, HttpServletRequest request) {
        // member db 확인
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        // add info (default - remote ip address, session id)
        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        // Context Holder 저장
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
