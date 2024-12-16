package com.backend.allreva.auth.application.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken,
        String refreshToken,
        String email,
        String profileImageUrl
) {

}

