package com.backend.allreva.auth.security;

import com.backend.allreva.auth.application.JwtService;
import com.backend.allreva.auth.exception.code.InvalidAccessTokenException;
import com.backend.allreva.auth.exception.code.InvalidRefreshTokenException;
import com.backend.allreva.common.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            @Value("${jwt.refresh.expiration}") final int refreshTime,
            @Value("${url.front.domain-name}") final String domainName,
            final JwtService jwtService,
            final UserDetailsService userDetailsService
    ) {
        this.refreshTime = refreshTime;
        this.domainName = domainName;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * JWT 토큰을 검증하고 권한을 부여합니다.
     */
    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        // 바디 및 쿠키로부터 토큰 추출
        String accessToken = jwtService.extractAccessToken(request);
        String refreshToken = jwtService.extractRefreshToken(request);

        // ANONYMOUS 요청 처리
        if (accessToken == null && refreshToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Refresh Token X
        try {
            jwtService.validateToken(refreshToken);
        } catch (CustomException e) {
            throw new InvalidRefreshTokenException();
        }
        // Access Token X, Refresh Token O
        try {
            jwtService.validateToken(accessToken);
        } catch (CustomException e) {
            throw new InvalidAccessTokenException();
        }
        // Access Token O, Refresh Token O
        String memberId = jwtService.extractMemberId(accessToken);

        setAuthenication(memberId, request);
        filterChain.doFilter(request, response);
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
