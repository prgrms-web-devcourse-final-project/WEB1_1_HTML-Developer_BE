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
        // token이 없다면 ANONYMOUS 로그인
        String refreshToken = jwtParser.getRefreshToken(request);
        String accessToken = jwtParser.getAccessToken(request);
        if (accessToken == null && refreshToken == null) {
            log.debug("GUEST login! {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
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

    private void setAuthenication(final String email, final HttpServletRequest request) {
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
