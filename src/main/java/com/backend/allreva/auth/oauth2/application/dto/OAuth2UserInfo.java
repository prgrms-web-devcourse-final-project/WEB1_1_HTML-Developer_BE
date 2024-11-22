package com.backend.allreva.auth.oauth2.application.dto;

import java.util.Map;

import com.backend.allreva.member.command.domain.LoginProvider;

import lombok.Builder;

@Builder
public record OAuth2UserInfo(
        LoginProvider loginProvider,
        String providerId,
        String nickname,
        String email,
        String profile) {

    public static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .loginProvider(LoginProvider.GOOGLE)
                .providerId((String) attributes.get("sub"))
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .build();
    }

    public static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        @SuppressWarnings("unchecked")
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        @SuppressWarnings("unchecked")
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        Long providerId = (Long) attributes.get("id");

        return OAuth2UserInfo.builder()
                .loginProvider(LoginProvider.KAKAO)
                .providerId(String.valueOf(providerId))
                .nickname((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .profile((String) attributes.get("profile_image_url"))
                .build();
    }
}
