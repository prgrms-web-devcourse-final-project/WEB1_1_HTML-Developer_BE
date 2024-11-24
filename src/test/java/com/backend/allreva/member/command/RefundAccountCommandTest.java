package com.backend.allreva.member.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.backend.allreva.member.command.application.MemberCommandService;
import com.backend.allreva.member.command.application.MemberRepository;
import com.backend.allreva.member.command.application.dto.RefundAccountRegisterRequest;
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
    private MemberCommandService memberCommandService;

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
        RefundAccountRegisterRequest refundAccountRegisterRequest = new RefundAccountRegisterRequest("땡땡은행", "123456789");

        // when
        Member registered = memberCommandService.registerRefundAccount(refundAccountRegisterRequest, member);

        // then
        assertThat(registered.getRefundAccount().getBank()).isEqualTo("땡땡은행");
    }

    @Test
    void 환불_계좌_정보를_성공적으로_수정한다() {
        // given
        RefundAccountRegisterRequest refundAccountRegisterRequest = new RefundAccountRegisterRequest("땡땡은행", "123456789");

        // when
        Member updated = memberCommandService.updateRefundAccount(refundAccountRegisterRequest, member);

        // then
        assertThat(updated.getRefundAccount().getBank()).isEqualTo("땡땡은행");
    }

    @Test
    void 환불_계좌_정보를_성공적으로_삭제한다() {
        // given
        willDoNothing().given(memberRepository).delete(any(Member.class));

        // when
        memberCommandService.deleteRefundAccount(member);

        // then
        verify(memberRepository, times(1)).delete(any(Member.class));
    }
}
