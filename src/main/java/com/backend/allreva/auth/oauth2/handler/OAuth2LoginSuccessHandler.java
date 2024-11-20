package com.backend.allreva.auth.oauth2.handler;

import java.io.IOException;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.backend.allreva.auth.application.dto.PrincipalDetails;
import com.backend.allreva.auth.application.dto.TokenResponse;
import com.backend.allreva.auth.util.JwtProvider;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    /**
     * OAuth2 인증 success시 JWT 반환하는 메서드
     *
     * OAuth2는 OAuth2UserService에서 이미 인증되기 때문에 별도의 인증 filter가 필요없다.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // OAuth2 인증된 사용자 정보 가져오기
        PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();
        Member member = oAuth2User.member();

        // token 생성
        String accessToken = jwtProvider.generateAccessToken(member.getEmail().getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(member.getEmail().getEmail());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // access token 응답객체 생성
        TokenResponse tokenResponse = TokenResponse.of(accessToken, refreshToken, jwtProvider.getREFRESH_TIME());

        // TODO: db or cache에 RefreshToken 저장

        // refreshToken 쿠키 등록
        setHeader(response, refreshToken);

        Response<TokenResponse> apiResponse = Response.onSuccess(tokenResponse);
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }

    // refreshToken 쿠키 설정
    public void setHeader(HttpServletResponse response, String refreshToken) {
        if (refreshToken != null) {
            response.addHeader("refresh_token", refreshToken);
            response.addHeader("Set-Cookie", createRefreshToken(refreshToken).toString());
        }
    }

    // refreshToken 쿠키 생성
    public static ResponseCookie createRefreshToken(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .maxAge(14 * 24 * 60 * 60 * 1000)
                .httpOnly(true)
                .build();
    }
}