package com.backend.allreva.auth.exception;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public CustomAuthenticationEntryPoint(
        @Qualifier("handlerExceptionResolver") final HandlerExceptionResolver resolver
    ) {
        this.resolver = resolver;
    }

    /**
     * 인증 실패 시 호출되는 메서드
     * 
     * JWT 토큰 인증 실패 에러가 발생합니다.
     * handlerExceptionResolver를 통해 예외를 처리합니다. (ControllerAdvice에서 처리)
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        Exception jwtException = (Exception) request.getAttribute("jakarta.servlet.error.exception");
        if (jwtException == null) {
            resolver.resolveException(request, response, null, authException);
        } else {
            resolver.resolveException(request, response, null, jwtException);
        }
    }
}
