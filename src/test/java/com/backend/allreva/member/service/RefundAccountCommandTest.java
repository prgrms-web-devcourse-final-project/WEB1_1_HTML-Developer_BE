package com.backend.allreva.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.backend.allreva.member.command.application.MemberInfoCommandService;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.member.command.domain.value.MemberRole;
import com.backend.allreva.member.fixture.MemberFixture;
import com.backend.allreva.member.fixture.MemberRequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class RefundAccountCommandTest {

    @InjectMocks
    private MemberInfoCommandService memberInfoCommandService;

    @Mock
    private MemberRepository memberRepository;

    Member member;

    @BeforeEach
    void setUp() {
        member = MemberFixture.createMemberFixture(1L, MemberRole.USER);
    }

    @Test
    void 환불_계좌_정보를_성공적으로_등록한다() {
        // given
        var refundAccountRequest = MemberRequestFixture.createRefundAccountRequest();
        given(memberRepository.save(any(Member.class))).willReturn(member);

        // when
        var registeredMember = memberInfoCommandService.registerRefundAccount(refundAccountRequest, member);

        // then
        assertThat(registeredMember.getRefundAccount().getBank()).isEqualTo("땡땡은행");
    }

    @Test
    void 환불_계좌_정보를_성공적으로_삭제한다() {
        // given
        given(memberRepository.save(any(Member.class))).willReturn(member);

        // when
        var deletedMember = memberInfoCommandService.deleteRefundAccount(member);

        // then
        assertThat(deletedMember.getRefundAccount().getBank()).isEmpty();
    }
}
