package com.backend.allreva.auth.filter;

import com.backend.allreva.auth.util.JwtParser;
import com.backend.allreva.auth.util.JwtValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@RequiredArgsConstructor
@Profile("!local")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtParser jwtParser;
    private final JwtValidator jwtValidator;
    private final UserDetailsService userDetailsService;

    /**
     * JWT 토큰을 검증하는 메서드
     */
    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        // 바디 및 쿠키로부터 토큰 추출
        String refreshToken = jwtParser.getRefreshToken(request);
        String accessToken = jwtParser.getAccessToken(request);

        // token이 없다면 ANONYMOUS 로그인
        if (accessToken == null && refreshToken == null) {
            log.debug("GUEST login! {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        // token 검증 수행
        String memberId = validateAndExtractMemberId(accessToken, refreshToken);

        // Authentication Security Holder에 저장
        setAuthenication(memberId, request);

        filterChain.doFilter(request, response);
    }

    private String validateAndExtractMemberId(String accessToken, String refreshToken) {
        if (accessToken != null) {
            jwtValidator.validateToken(accessToken);
            return jwtParser.extractMemberId(accessToken);
        }
        if (refreshToken != null) {
            // TODO: refreshToken validation 적용
            jwtValidator.validateToken(refreshToken);
            return jwtParser.extractMemberId(refreshToken);
        }
        return null;
    }

    private void setAuthenication(final String memberId, final HttpServletRequest request) {
        // member db 확인
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        // add info (default - remote ip address, session id)
        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        // Context Holder 저장
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
