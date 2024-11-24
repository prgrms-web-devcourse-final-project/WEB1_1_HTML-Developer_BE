package com.backend.allreva.member.command.application.dto;

public record MemberInfoUpdateRequest(
        String nickname,
        String introduce,
        String profileImageUrl) {

}
