package com.backend.allreva.auth.filter;

import static com.backend.allreva.common.config.SecurityEndpointPaths.ANOMYMOUS_LIST;
import static com.backend.allreva.common.config.SecurityEndpointPaths.ANOMYMOUS_LIST_GET;
import static com.backend.allreva.common.config.SecurityEndpointPaths.WHITE_LIST;

import com.backend.allreva.auth.application.JwtService;
import com.backend.allreva.auth.exception.code.InvalidJwtTokenException;
import com.backend.allreva.common.util.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;
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
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@Profile("!local")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final int ACCESS_TIME;
    private final int REFRESH_TIME;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            @Value("${jwt.access.expiration}") final int ACCESS_TIME,
            @Value("${jwt.refresh.expiration}") final int REFRESH_TIME,
            final JwtService jwtService,
            final UserDetailsService userDetailsService
    ) {
        this.ACCESS_TIME = ACCESS_TIME;
        this.REFRESH_TIME = REFRESH_TIME;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 화이트 리스트에 포함된 요청은 필터링 하지 않습니다.
     */
    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        return Stream.of(WHITE_LIST, ANOMYMOUS_LIST, ANOMYMOUS_LIST_GET)
                .flatMap(Arrays::stream)
                .anyMatch(path -> antPathMatcher.match(path, request.getRequestURI()));
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

        // 토큰 검증 결과 boolean 변수로 분리
        boolean isAccessTokenValid = jwtService.validateToken(accessToken);
        boolean isRefreshTokenValid = jwtService.validateToken(refreshToken);

        String memberId = "";

        // Access도 Refresh도 둘 다 유효하지 않으면 예외 발생
        if (!isAccessTokenValid && !isRefreshTokenValid) {
            throw new InvalidJwtTokenException();
        }

        // Access Token이 유효하지 않으나, Refresh Token은 유효한 경우 => Access Token 및 Refresh Token 재발급
        if (!isAccessTokenValid) {
            memberId = jwtService.extractMemberId(refreshToken);
            reissueAccessToken(memberId, response);
            reissueRefreshToken(memberId, response);
        }

        // Access Token이 유효하고, Refresh Token이 유효하지 않은 경우 => Refresh Token 재발급
        if (isAccessTokenValid && !isRefreshTokenValid) {
            memberId = jwtService.extractMemberId(accessToken);
            reissueRefreshToken(memberId, response);
        }

        if (isAccessTokenValid && isRefreshTokenValid) {
            memberId = jwtService.extractMemberId(accessToken);
        }

        // Authentication Security Holder에 저장
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
        CookieUtils.addCookie(response, "refreshToken", generatedRefreshToken, REFRESH_TIME);
        jwtService.updateRefreshToken(generatedRefreshToken, memberId);
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
        CookieUtils.addCookie(response, "accessToken", generatedAccessToken, ACCESS_TIME);
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
