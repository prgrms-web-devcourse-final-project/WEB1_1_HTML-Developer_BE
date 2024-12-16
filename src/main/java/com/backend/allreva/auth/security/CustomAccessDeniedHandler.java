package com.backend.allreva.auth.security;

import com.backend.allreva.auth.exception.code.CustomAccessDeniedException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver resolver;

    public CustomAccessDeniedHandler(
            @Qualifier("handlerExceptionResolver") final HandlerExceptionResolver resolver
    ) {
        this.resolver = resolver;
    }

    /**
     * 유효하지 않는 권한일 때 호출됩니다. (403 Forbidden)
     */
    @Override
    public void handle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        Exception exception = (Exception) request.getAttribute("jakarta.servlet.error.exception");
        if (exception != null) {
            resolver.resolveException(request, response, null, exception);
            return;
        }
        CustomAccessDeniedException customAccessDeniedException = new CustomAccessDeniedException(
                accessDeniedException.getLocalizedMessage());
        resolver.resolveException(request, response, null, customAccessDeniedException);
    }
}
