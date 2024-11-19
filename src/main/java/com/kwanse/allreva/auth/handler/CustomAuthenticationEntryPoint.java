package com.kwanse.allreva.auth.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwanse.allreva.common.dto.Response;
import com.kwanse.allreva.common.exception.code.GlobalErrorCode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 인증 실패 시 호출되는 메서드
     * 
     * JWT 토큰 인증 실패 시 UNAUTHORIZED 상태로 응답합니다.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        log.info(authException.getMessage());

        Response<GlobalErrorCode> errorResponse = Response.onFailure(
                GlobalErrorCode.UNAUTHORIZED_ERROR.getErrorCode().code(),
                GlobalErrorCode.UNAUTHORIZED_ERROR.getErrorCode().message()
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
