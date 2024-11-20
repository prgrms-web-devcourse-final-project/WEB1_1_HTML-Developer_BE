package com.backend.allreva.member.command.domain;

import com.backend.allreva.common.model.Email;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private MemberRole memberRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private LoginProvider loginProvider;

    @Embedded
    private MemberInfo memberInfo;

    @Builder
    private Member(
        Email email, 
        MemberRole memberRole, 
        LoginProvider loginProvider,
        String nickname,
        String introduce,
        String profileImageUrl
    ) {
        this.email = email;
        this.memberRole = memberRole;
        this.loginProvider = loginProvider;
        this.memberInfo = MemberInfo.builder()
                .nickname(nickname)
                .introduce(introduce)
                .profileImageUrl(profileImageUrl)
                .build();
    }

    /**
     * 임시 GUEST 생성 메서드
     *
     * 아직 회원가입이 완전히 이루어진 상태가 아니기 때문에 GUEST 권한으로 등록되어 있습니다.
     * 회원가입에 필요한 정보들을 모두 기입할 시 USER 권한으로 승격됩니다.
     * - password 없음
     */
    public static Member createTemporary(
            String email,
            String nickname,
            LoginProvider loginProvider
    ) {
        return Member.builder()
                .email(Email.builder()
                    .email(email)
                    .build())
                .nickname(nickname)
                .loginProvider(loginProvider)
                .memberRole(MemberRole.GUEST)
                .build();
    }

    /**
     * 회원 가입 이후 회원 정보 기입 메서드
     * 
     * 회원 가입 이후 회원 정보를 기입하면 회원 권한이 USER로 승격됩니다.
     */
    public void updateMemberInfo(
        String nickname,
        String introduce,
        String profileImageUrl
    ) {
        this.memberInfo = MemberInfo.builder()
                .nickname(nickname)
                .introduce(introduce)
                .profileImageUrl(profileImageUrl)
                .build();
        upgradeToUser();
    }

    private void upgradeToUser() {
        this.memberRole = MemberRole.USER;
    }
}
