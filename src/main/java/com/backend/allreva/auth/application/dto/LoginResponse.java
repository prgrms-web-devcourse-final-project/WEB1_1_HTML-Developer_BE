package com.backend.allreva.auth.application.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        boolean isUser,
        String email,
        String nickname,
        String profileImageUrl,
        String accessToken,
        String refreshToken
) {

}
