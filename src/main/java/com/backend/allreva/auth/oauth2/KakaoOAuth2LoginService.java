package com.backend.allreva.auth.oauth2;

import com.backend.allreva.auth.application.OAuth2LoginService;
import com.backend.allreva.auth.application.dto.UserInfo;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2LoginService implements OAuth2LoginService {

    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoUserInfoClient kakaoUserInfoClient;

    @Value("${oauth2.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    @Value("${oauth2.kakao.client-id}")
    private String kakaoClientId;
    @Value("${oauth2.kakao.client-secret}")
    private String kakaoClientSecret;

    /**
     * 카카오 로그인 시 사용자 정보를 가져옵니다.
     * @param authorizationCode 인가 코드
     * @return 사용자 정보
     */
    @Override
    public UserInfo getUserInfo(final String authorizationCode) {
        KakaoToken token = kakaoAuthClient.getToken(
                kakaoClientId,
                kakaoRedirectUri,
                authorizationCode,
                "authorization_code",
                kakaoClientSecret
        );

        KakaoUserInfo kakaoUserInfo = kakaoUserInfoClient.getUserInfo(
                "Bearer " + token.accessToken()
        );

        return UserInfo.builder()
                .loginProvider(LoginProvider.KAKAO)
                .providerId(kakaoUserInfo.id())
                .email(kakaoUserInfo.kakaoAccount().email())
                .nickname(kakaoUserInfo.kakaoAccount().profile().nickname())
                .profileImageUrl(kakaoUserInfo.kakaoAccount().profile().profileImageUrl())
                .build();
    }
}
