package com.backend.allreva.auth.exception;

import com.backend.allreva.auth.exception.code.UnauthorizedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") final HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * 인증 실패 시 호출되는 메서드입니다. (401 Unauthorized)
     */
    @Override
    public void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException
    ) throws IOException, ServletException {
        Exception jwtException = (Exception) request.getAttribute("jakarta.servlet.error.exception");
        if (jwtException != null) {
            resolver.resolveException(request, response, null, jwtException);
            return;
        }
        UnauthorizedException unauthorizedException = new UnauthorizedException(authException.getLocalizedMessage());
        resolver.resolveException(request, response, null, unauthorizedException);
    }
}
