package com.backend.allreva.survey.command.application;

import com.backend.allreva.IntegralTestSupport;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.command.domain.exception.ConcertNotFoundException;
import com.backend.allreva.member.command.application.MemberRepository;
import com.backend.allreva.member.command.domain.Member;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

class SurveyCommandServiceTest extends IntegralTestSupport {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SurveyCommandService surveyCommandService;
    @Autowired
    private SurveyCommandRepository surveyCommandRepository;
    @Autowired
    private ConcertRepository concertRepository;
    private Member testMember;
    private Concert testConcert;

    @BeforeEach
    void setUp() {
        testMember = createTestMember();
        testConcert = createTestConcert();
        memberRepository.save(testMember);
        concertRepository.save(testConcert);
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
                testConcert.getId(),
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

    @Test
    @DisplayName("콘서트를 찾을 수 없어 수요조사 폼 개설에 성공한다.")
    public void failOpenSurvey() {
        // Given
        OpenSurveyRequest openSurveyRequest = new OpenSurveyRequest(
                "하현상 콘서트: Elegy [서울]",
                200000L,
                of("2024.11.30(토)", "2024.12.01(일)"),
                "하현상",
                Region.SEOUL,
                LocalDate.now(),
                25,
                "이틀 모두 운영합니다."
        );

        // When
        assertThrows(ConcertNotFoundException.class, () -> {
            surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        });
    }
}