package com.backend.allreva.auth.application.dto;

import lombok.Builder;

@Builder
public record LoginSuccessResponse(
        String accessToken,
        String refreshToken,
        Long refreshTokenExpirationTime,
        String email,
        String profileImageUrl
) {

    public static LoginSuccessResponse of(
            final String accessToken,
            final String refreshToken,
            final Long refreshTokenExpirationTime,
            final String email,
            final String profileImageUrl
    ) {
        return LoginSuccessResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .email(email)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}

