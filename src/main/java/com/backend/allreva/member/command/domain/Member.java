package com.backend.allreva.member.command.domain;

import com.backend.allreva.common.application.BaseEntity;
import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import com.backend.allreva.member.command.domain.value.MemberInfo;
import com.backend.allreva.member.command.domain.value.MemberRole;
import com.backend.allreva.member.command.domain.value.RefundAccount;
import com.backend.allreva.member.infra.converter.RefundAccountConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id = ?")
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole memberRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private LoginProvider loginProvider;

    @Embedded
    private MemberInfo memberInfo;

    @Convert(converter = RefundAccountConverter.class)
    @Column(name = "refund_account")
    private RefundAccount refundAccount;

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
     * <p>
     * 아직 회원가입이 완전히 이루어진 상태가 아니기 때문에 GUEST 권한으로 등록되어 있습니다. 회원가입에 필요한 정보들을 모두 기입할 시 USER 권한으로 승격됩니다. - password 없음
     */
    public static Member createTemporary(
            String email,
            String nickname,
            LoginProvider loginProvider,
            String profileImageUrl
    ) {
        return Member.builder()
                .email(Email.builder()
                        .email(email)
                        .build())
                .nickname(nickname)
                .loginProvider(loginProvider)
                .memberRole(MemberRole.GUEST)
                .profileImageUrl(profileImageUrl)
                .build();
    }

    public void setMemberInfo(String nickname, String introduce, String profileImageUrl) {
        this.memberInfo = MemberInfo.builder()
                .nickname(nickname)
                .introduce(introduce)
                .profileImageUrl(profileImageUrl)
                .build();
    }

    public void setRefundAccount(String bank, String number) {
        this.refundAccount = RefundAccount.builder()
                .bank(bank)
                .number(number)
                .build();
    }

    public void upgradeToUser() {
        if (this.memberRole.equals(MemberRole.GUEST)) {
            this.memberRole = MemberRole.USER;
        }
    }
}
