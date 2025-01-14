package com.backend.allreva.member.command.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    DEVELOPER("ROLE_DEVELOPER");

    private final String text;
}
