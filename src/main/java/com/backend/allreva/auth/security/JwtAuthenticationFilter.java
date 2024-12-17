package com.backend.allreva.auth.security;

import com.backend.allreva.auth.application.JwtService;
import com.backend.allreva.auth.exception.code.InvalidJwtTokenException;
import com.backend.allreva.common.util.CookieUtils;
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

    private final int refreshTime;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            @Value("${jwt.refresh.expiration}") final int refreshTime,
            final JwtService jwtService,
            final UserDetailsService userDetailsService
    ) {
        this.refreshTime = refreshTime;
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

        // Refresh Token 검증
        boolean isRefreshTokenValid = jwtService.validateToken(refreshToken);
        if (!isRefreshTokenValid) {
            throw new InvalidJwtTokenException();
        }

        // Access Token 검증
        boolean isAccessTokenValid = jwtService.validateToken(accessToken);
        String memberId;

        // Access Token X, Refresh Token O => Access Token 및 Refresh Token 재발급
        if (!isAccessTokenValid) {
            memberId = jwtService.extractMemberId(refreshToken);
            reissueAccessToken(memberId, response);
            reissueRefreshToken(memberId, response); // token rotate
        }
        // Access Token O, Refresh Token O
        else {
            memberId = jwtService.extractMemberId(accessToken);
        }

        setAuthenication(memberId, request);
        filterChain.doFilter(request, response);
    }

    /**
     * Refresh Token을 재발급합니다.
     * @param memberId 사용자 ID
     * @param response HTTP 응답 객체
     */
    private void reissueRefreshToken(
            final String memberId,
            final HttpServletResponse response
    ) {
        String generatedRefreshToken = jwtService.generateRefreshToken(memberId);
        CookieUtils.addCookie(response, "refreshToken", generatedRefreshToken, refreshTime);
        jwtService.updateRefreshToken(generatedRefreshToken, Long.valueOf(memberId));
    }

    /**
     * Access Token을 재발급합니다.
     * @param memberId 사용자 ID
     * @param response HTTP 응답 객체
     */
    private void reissueAccessToken(
            final String memberId,
            final HttpServletResponse response
    ) {
        String generatedAccessToken = jwtService.generateAccessToken(memberId);
        response.addHeader("Authorization", "Bearer " + generatedAccessToken);
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
