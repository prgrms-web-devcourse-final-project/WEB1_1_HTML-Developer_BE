package com.backend.allreva;

import com.backend.allreva.common.config.JpaAuditingConfig;
import com.backend.allreva.common.model.Email;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.value.*;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import com.backend.allreva.member.command.domain.value.MemberRole;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@MockBean(JpaAuditingConfig.class)
public abstract class IntegralTestSupport {
    // 테스트용 Member 객체 생성
    protected Member createTestMember() {
        return Member.builder()
                .email(new Email("example@example.com"))
                .memberRole(MemberRole.USER)
                .loginProvider(LoginProvider.GOOGLE)
                .nickname("JohnDoe")
                .introduce("Hello, I'm John.")
                .profileImageUrl("http://example.com/profile.jpg")
                .build();
    }

    // 테스트용 Concert 객체 생성
    protected Concert createTestConcert() {
        return Concert.builder()
                .code(Code.builder()
                        .hallCode("123")
                        .concertCode("456")
                        .build())
                .concertInfo(new ConcertInfo("Sample Concert", "2024-12-01", ConcertStatus.IN_PROGRESS, "host",
                        new DateInfo(LocalDate.of(2024, 11, 30), LocalDate.of(2024, 12, 1), "timetable")))
                .poster(new Image("http://example.com/poster.jpg"))
                .detailImages(List.of(new Image("http://example.com/detail1.jpg"), new Image("http://example.com/detail2.jpg")))
                .sellers(List.of(new Seller("Sample Seller", "http://seller.com")))
                .build();
    }
}
