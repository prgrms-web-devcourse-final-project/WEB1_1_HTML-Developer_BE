package com.backend.allreva.member.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.backend.allreva.member.command.application.MemberInfoCommandService;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.member.command.application.request.RefundAccountRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
public class RefundAccountCommandTest {

    @InjectMocks
    private MemberInfoCommandService memberInfoCommandService;

    @Mock
    private MemberRepository memberRepository;

    Member member;

    @BeforeEach
    void setUp() {
        member = Member.createTemporary(
                "my@email",
                "nickname",
                LoginProvider.GOOGLE,
                "https://my_picture");
        ReflectionTestUtils.setField(member, "id", 1L);
        member.upgradeToUser();
    }

    @Test
    void 환불_계좌_정보를_성공적으로_등록한다() {
        // given
        var refundAccountRequest = new RefundAccountRequest("땡땡은행", "123456789");
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
        assertThat(deletedMember.getRefundAccount().getBank()).isEqualTo("");
    }
}
