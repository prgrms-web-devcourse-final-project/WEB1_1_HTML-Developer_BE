package com.backend.allreva.auth.oauth2.handler;

import com.backend.allreva.auth.application.dto.LoginSuccessResponse;
import com.backend.allreva.auth.application.dto.PrincipalDetails;
import com.backend.allreva.auth.util.CookieUtil;
import com.backend.allreva.auth.util.JwtProvider;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.MemberRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String FRONT_BASE_URL = "http://localhost:3000";
    private static final String FRONT_SIGNUP_URL = "/signup";

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    @Value("${jwt.refresh.expiration}")
    private int REFRESH_TIME;

    /**
     * OAuth2 인증 success시 JWT 반환하는 메서드
     *
     * OAuth2는 OAuth2UserService에서 이미 인증되기 때문에 별도의 인증 filter가 필요없다.
     */
    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication
    ) throws IOException, ServletException {
        // OAuth2 인증된 사용자 정보 가져오기
        PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();
        Member member = oAuth2User.member();

        // token 생성
        String memberId = String.valueOf(member.getId());
        String accessToken = jwtProvider.generateAccessToken(memberId);
        String refreshToken = jwtProvider.generateRefreshToken(memberId);

        // access token 응답객체 생성
        LoginSuccessResponse loginSuccessResponse = LoginSuccessResponse.of(
                accessToken,
                refreshToken,
                jwtProvider.getREFRESH_TIME(),
                member.getEmail().getEmail(),
                member.getMemberInfo().getProfileImageUrl()
        );

        // TODO: db or cache에 RefreshToken 저장

        // refreshToken 쿠키 등록
        CookieUtil.addCookie(response, "refreshToken", refreshToken, REFRESH_TIME);

        setResponseBody(response, loginSuccessResponse);
        if (member.getMemberRole().equals(MemberRole.USER)) {
            response.sendRedirect(FRONT_BASE_URL);
        } else {
            response.sendRedirect(FRONT_BASE_URL + FRONT_SIGNUP_URL);
        }
    }

    private void setResponseBody(
            final HttpServletResponse response,
            final LoginSuccessResponse loginSuccessResponse
    ) throws IOException {
        Response<LoginSuccessResponse> apiResponse = Response.onSuccess(loginSuccessResponse);
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}