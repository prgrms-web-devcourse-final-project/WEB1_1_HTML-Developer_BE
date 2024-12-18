package com.backend.allreva.auth.application.dto;

import com.backend.allreva.member.command.domain.value.LoginProvider;
import lombok.Builder;

@Builder
public record UserInfo(
        LoginProvider loginProvider,
        String providerId,
        String nickname,
        String email,
        String profileImageUrl
) {

}
