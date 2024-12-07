package com.backend.allreva.auth.filter;

import com.backend.allreva.auth.domain.RefreshToken;
import com.backend.allreva.auth.domain.RefreshTokenRepository;
import com.backend.allreva.auth.exception.code.InvalidJwtTokenException;
import com.backend.allreva.auth.util.CookieUtil;
import com.backend.allreva.auth.util.JwtParser;
import com.backend.allreva.auth.util.JwtProvider;
import com.backend.allreva.auth.util.JwtValidator;
import com.backend.allreva.common.config.SecurityEndpointPaths;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
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
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!local")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtParser jwtParser;
    private final JwtValidator jwtValidator;
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.expiration}")
    private int REFRESH_TIME;

    @Value("${jwt.access.expiration}")
    private int ACCESS_TIME;

    /**
     * 화이트 리스트에 포함된 요청은 필터링 하지 않는다.
     */
    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        return Arrays.stream(SecurityEndpointPaths.WHITE_LIST)
                .anyMatch(path -> PatternMatchUtils.simpleMatch(path, request.getRequestURI()));
    }

    /**
     * JWT 토큰을 검증하고 권한을 부여한다.
     */
    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        // 바디 및 쿠키로부터 토큰 추출
        String accessToken = jwtParser.getAccessToken(request);
        String refreshToken = jwtParser.getRefreshToken(request);

        // token이 없다면 ANONYMOUS 로그인
        if (accessToken == null && refreshToken == null) {
            log.debug("GUEST login! {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 검증 결과 boolean 변수로 분리
        boolean isAccessTokenInvalid = jwtValidator.isTokenInValid(accessToken);
        boolean isRefreshTokenInvalid = jwtValidator.isTokenInValid(refreshToken);

        String memberId;

        if (isAccessTokenInvalid && isRefreshTokenInvalid) {
            // Access도 Refresh도 둘 다 유효하지 않으면 예외 발생
            throw new InvalidJwtTokenException();
        }

        if (isAccessTokenInvalid) {
            // Access Token이 유효하지 않으나, Refresh Token은 유효한 경우 => Access Token 재발급
            memberId = jwtParser.extractMemberId(refreshToken);
            String generatedAccessToken = jwtProvider.generateAccessToken(memberId);
            CookieUtil.addCookie(response, "accessToken", generatedAccessToken, ACCESS_TIME);
        } else {
            // Access Token이 유효한 경우
            memberId = jwtParser.extractMemberId(accessToken);
            if (isRefreshTokenInvalid) {
                // Refresh Token이 유효하지 않다면 => Refresh Token 재발급
                String generatedRefreshToken = jwtProvider.generateRefreshToken(memberId);
                CookieUtil.addCookie(response, "refreshToken", generatedRefreshToken, REFRESH_TIME);
                refreshTokenRepository.findRefreshTokenByMemberId(Long.valueOf(memberId))
                        .ifPresent(refreshTokenRepository::delete);
                refreshTokenRepository.save(RefreshToken.builder()
                        .token(generatedRefreshToken)
                        .memberId(Long.valueOf(memberId))
                        .build());
            }
        }

        // Authentication Security Holder에 저장
        setAuthenication(memberId, request);

        filterChain.doFilter(request, response);
    }

    /**
     * 사용자 인증을 수행하고 SecurityContextHolder에 저장한다.
     * @param memberId 사용자 ID
     * @param request HTTP 요청 객체
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
