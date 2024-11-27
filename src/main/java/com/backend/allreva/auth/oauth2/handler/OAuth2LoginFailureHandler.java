package com.backend.allreva.auth.oauth2.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.common.exception.code.GlobalErrorCode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {

    private final HandlerExceptionResolver resolver;

    public OAuth2LoginFailureHandler(
        @Qualifier("handlerExceptionResolver") final HandlerExceptionResolver resolver
    ) {
        this.resolver = resolver;
    }

    @Override
    public void onAuthenticationFailure(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException exception
    ) throws IOException, ServletException {
        log.info("OAuth2 로그인 실패 예외 발생: {}", exception.getMessage());
        resolver.resolveException(request, response, null, new CustomException(GlobalErrorCode.UNAUTHORIZED_ERROR));
    }
}
