package com.backend.allreva.auth.exception;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.GlobalErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    /**
     * 인증 실패 시 호출되는 메서드
     * 
     * JWT 토큰 인증 실패 에러가 발생합니다.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        Exception jwtException = (Exception) request.getAttribute("jakarta.servlet.error.exception");

        ErrorCode errorCode;
        String message;

        if (jwtException != null && jwtException instanceof CustomException) {
            CustomException customException = (CustomException) jwtException;
            errorCode = customException.getErrorCode();
            message = errorCode.message();
            log.info("JWT 인증 예외 발생: {}", message);
        } else {
            errorCode = GlobalErrorCode.UNAUTHORIZED_ERROR.getErrorCode();
            message = errorCode.message();
            log.info("기본 인증 예외 발생: {}", message);
        }

        Response<GlobalErrorCode> errorResponse = Response.onFailure(
                errorCode.code(), message
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
