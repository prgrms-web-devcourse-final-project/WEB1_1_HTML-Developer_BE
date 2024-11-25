package com.backend.allreva.survey.command.application;

import com.backend.allreva.IntegralTestSupport;
import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.application.MemberRepository;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import com.backend.allreva.member.command.domain.value.MemberRole;
import com.backend.allreva.survey.command.application.dto.OpenSurveyRequest;
import com.backend.allreva.survey.command.application.dto.SurveyIdResponse;
import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.survey.command.domain.value.Region;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static java.util.List.of;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SurveyCommandServiceTest extends IntegralTestSupport {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SurveyCommandService surveyCommandService;
    @Autowired
    private SurveyCommandRepository surveyCommandRepository;
    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = Member.builder()
                .email(new Email("example@example.com"))
                .memberRole(MemberRole.USER)
                .loginProvider(LoginProvider.GOOGLE)
                .nickname("JohnDoe")
                .introduce("Hello, I'm John.")
                .profileImageUrl("http://example.com/profile.jpg")
                .build();
        memberRepository.save(testMember);
    }

    @AfterEach
    void tearDown() {
        memberRepository.flush();
        surveyCommandRepository.flush();
    }

    @Test
    @DisplayName("수요조사 폼 개설에 성공한다.")
    public void openSurvey() {
        // Given
        OpenSurveyRequest openSurveyRequest = new OpenSurveyRequest(
                "하현상 콘서트: Elegy [서울]",
                1L,
                of("2024.11.30(토)", "2024.12.01(일)"),
                "하현상",
                Region.SEOUL,
                LocalDate.now(),
                25,
                "이틀 모두 운영합니다."
        );

        // When
        SurveyIdResponse response = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        Survey savedSurvey = surveyCommandRepository.findById(response.surveyId()).orElse(null);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.surveyId());
        assertNotNull(savedSurvey);
        assertEquals("하현상 콘서트: Elegy [서울]", savedSurvey.getTitle());
    }
}