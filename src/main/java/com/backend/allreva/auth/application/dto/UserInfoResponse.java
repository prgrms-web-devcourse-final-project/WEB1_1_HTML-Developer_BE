package com.backend.allreva.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

@Builder
public record UserInfoResponse(
        boolean isUser,
        String email,
        String nickname,
        String profileImageUrl,
        @JsonIgnore
        String accessToken,
        @JsonIgnore
        String refreshToken
) {

}
