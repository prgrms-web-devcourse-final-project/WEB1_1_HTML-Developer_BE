package com.backend.allreva.auth.application.dto;

import lombok.Builder;

@Builder
public record RefreshTokenRequest(
        String refreshToken
) {

}
