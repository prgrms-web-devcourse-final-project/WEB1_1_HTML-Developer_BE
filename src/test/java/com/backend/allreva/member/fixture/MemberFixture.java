package com.backend.allreva.member.fixture;

import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import com.backend.allreva.member.command.domain.value.MemberRole;
import org.springframework.test.util.ReflectionTestUtils;

public final class MemberFixture {

    public static Member createMemberFixture(
            final Long memberId,
            final MemberRole memberRole
    ) {
        var member = Member.builder()
                .email(Email.builder().email("my@email").build())
                .nickname("nickname")
                .memberRole(memberRole)
                .loginProvider(LoginProvider.GOOGLE)
                .introduce("introduce")
                .profileImageUrl("imageUrl")
                .build();
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }
}
