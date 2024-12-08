package com.backend.allreva.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.backend.allreva.common.application.S3ImageService;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.member.command.application.MemberInfoCommandService;
import com.backend.allreva.member.command.application.request.MemberInfoRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import com.backend.allreva.member.command.domain.value.MemberRole;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberInfoCommandTest {

    @InjectMocks
    private MemberInfoCommandService memberInfoCommandService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private S3ImageService s3ImageService;

    Member member;

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
    void 회원_가입_시_회원_정보를_성공적으로_등록한다() {
        // given
        var uploadedImage = new Image("https://my_picture");
        var memberInfoRequest = new MemberInfoRequest(
                "updated nickname",
                "test introduce",
                List.of()
        );
        given(memberRepository.save(any(Member.class))).willReturn(member);

        // when
        var updatedMember = memberInfoCommandService.registerMember(memberInfoRequest, member, uploadedImage);

        // then
        assertSoftly(softly -> {
            softly.assertThat(updatedMember.getMemberInfo().getIntroduce()).isEqualTo("test introduce");
            softly.assertThat(updatedMember.getMemberRole()).isEqualTo(MemberRole.USER);
        });
    }

    @Test
    void 회원_정보를_성공적으로_수정한다() {
        // given
        var uploadedImage = new Image("https://my_picture");
        var memberInfoRequest = new MemberInfoRequest(
                "updated nickname",
                "test introduce",
                List.of()
        );
        given(memberRepository.save(any(Member.class))).willReturn(member);

        // when
        Member updatedMember = memberInfoCommandService.updateMemberInfo(memberInfoRequest, member, uploadedImage);

        // then
        assertThat(updatedMember.getMemberInfo().getIntroduce()).isEqualTo("test introduce");
    }
}
