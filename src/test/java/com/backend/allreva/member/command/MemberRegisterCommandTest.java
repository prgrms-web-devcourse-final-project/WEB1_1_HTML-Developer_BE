package com.backend.allreva.member.command;

import static org.assertj.core.api.Assertions.assertThat;

import com.backend.allreva.member.command.application.MemberCommandService;
import com.backend.allreva.member.command.application.dto.MemberInfoUpdateRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

@SuppressWarnings("NonAsciiCharacters")
public class MemberRegisterCommandTest {

    private final MemberCommandService memberCommandService;

    Member member;

    public MemberRegisterCommandTest() {
        this.memberCommandService = new MemberCommandService();
    }

    @BeforeEach
    void setUp() {
        member = Member.createTemporary(
                "my@email",
                "nickname",
                LoginProvider.GOOGLE,
                "https://my_picture");
        ReflectionTestUtils.setField(member, "id", 1L);
    }

    @Test
    void 회원_가입_시_회원_정보를_성공적으로_입력한다() {
        // given
        MemberInfoUpdateRequest memberInfoUpdateRequest = new MemberInfoUpdateRequest(
                "updated nickname",
                "test introduce",
                "updated profile image url"
        );

        // when
        Member updatedMember = memberCommandService.updateMemberInfo(memberInfoUpdateRequest, member);

        // then
        assertThat(updatedMember.getMemberInfo().getIntroduce()).isEqualTo("test introduce");
    }
}
