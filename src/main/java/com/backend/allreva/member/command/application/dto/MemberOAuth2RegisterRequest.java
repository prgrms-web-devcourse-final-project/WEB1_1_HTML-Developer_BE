package com.backend.allreva.member.command.application.dto;

public record MemberOAuth2RegisterRequest(
                String nickname,
                String introduce,
                String profileImageUrl) {
}
