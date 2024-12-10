package com.backend.allreva.auth.oauth2.handler;

import com.backend.allreva.auth.application.JwtService;
import com.backend.allreva.auth.application.dto.PrincipalDetails;
import com.backend.allreva.auth.domain.RefreshToken;
import com.backend.allreva.auth.domain.RefreshTokenRepository;
import com.backend.allreva.common.util.CookieUtils;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.MemberRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String FRONT_BASE_URL = "https://allreva.shop"; //http://localhost:3000
    private static final String FRONT_SIGNUP_URL = "/signup";

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.refresh.expiration}")
    private int REFRESH_TIME;
    @Value("${jwt.access.expiration}")
    private int ACCESS_TIME;

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
        String accessToken = jwtService.generateAccessToken(String.valueOf(memberId));
        String refreshToken = jwtService.generateRefreshToken(String.valueOf(memberId));

        // redis에 RefreshToken 저장
        refreshTokenRepository.findRefreshTokenByMemberId(memberId)
                .ifPresent(refreshTokenRepository::delete);
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .memberId(memberId)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);

        // refreshToken 쿠키 등록
        CookieUtils.addCookie(response, "accessToken", accessToken, ACCESS_TIME);
        CookieUtils.addCookie(response, "refreshToken", refreshToken, REFRESH_TIME);

        sendRedirect(response, member, accessToken);
    }

    private void sendRedirect(
            final HttpServletResponse response,
            final Member member,
            final String accessToken
    ) throws IOException {
        if (member.getMemberRole().equals(MemberRole.USER)) {
            response.sendRedirect(FRONT_BASE_URL + "/auth-success?accessToken=" + URLEncoder.encode(accessToken, StandardCharsets.UTF_8));
        } else {
            response.sendRedirect(FRONT_BASE_URL + FRONT_SIGNUP_URL + "/auth-success?accessToken=" + URLEncoder.encode(accessToken, StandardCharsets.UTF_8));
        }
    }
}