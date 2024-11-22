package com.backend.allreva.member.command.application.dto;

public record MemberOAuth2RegisterRequest(
                Long memberId,
                String nickname,
                String introduce,
                String profileImageUrl) {
}
