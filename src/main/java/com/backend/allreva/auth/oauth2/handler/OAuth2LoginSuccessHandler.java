package com.backend.allreva.auth.oauth2.handler;

import com.backend.allreva.auth.application.dto.PrincipalDetails;
import com.backend.allreva.auth.domain.RefreshToken;
import com.backend.allreva.auth.domain.RefreshTokenRepository;
import com.backend.allreva.auth.util.CookieUtil;
import com.backend.allreva.auth.util.JwtProvider;
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

    private static final String FRONT_BASE_URL = "http://localhost:8080";
    private static final String FRONT_SIGNUP_URL = "/signup";

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.refresh.expiration}")
    private int REFRESH_TIME;
    @Value("${jwt.access.expiration}")
    private int ACCESS_TIME;
    @Value("${jwt.refresh.cookie-name}")
    private String REFRESH_COOKIE_NAME;

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
        Long memberId = member.getId();
        String accessToken = jwtProvider.generateAccessToken(String.valueOf(memberId));
        String refreshToken = jwtProvider.generateRefreshToken(String.valueOf(memberId));

        // redis에 RefreshToken 저장
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .memberId(memberId)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);

        // refreshToken 쿠키 등록
        CookieUtil.addCookie(response, "accessToken", accessToken, ACCESS_TIME);
        CookieUtil.addCookie(response, REFRESH_COOKIE_NAME, refreshToken, REFRESH_TIME);

        sendRedirect(response, member);
    }

    private void sendRedirect(
            final HttpServletResponse response,
            final Member member
    ) throws IOException {
        if (member.getMemberRole().equals(MemberRole.USER)) {
            response.sendRedirect(FRONT_BASE_URL);
        } else {
            response.sendRedirect(FRONT_BASE_URL + FRONT_SIGNUP_URL);
        }
    }
}