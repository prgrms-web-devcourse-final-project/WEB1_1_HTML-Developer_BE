package com.backend.allreva.member.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberInfo {

    private String nickname;
    private String introduce;
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Builder
    private MemberInfo(String nickname, String introduce, String profileImageUrl) {
        this.nickname = nickname;
        this.introduce = introduce;
        this.profileImageUrl = profileImageUrl;
    }
}
