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
import com.backend.allreva.survey.exception.SurveyNotFoundException;
import com.backend.allreva.survey.exception.SurveyNotWriterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static java.util.List.of;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

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
        surveyCommandRepository.deleteAll();
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
        assertNotNull(savedSurvey);
        assertEquals("하현상 콘서트: Elegy [서울]", savedSurvey.getTitle());
    }

    @Test
    @DisplayName("콘서트를 찾을 수 없어 수요조사 폼 개설에 실패한다.")
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

    @Test
    @DisplayName("수요조사 폼 삭제에 성공한다.")
    public void removeSurvey() {
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
        surveyCommandRepository.flush();
        surveyCommandService.removeSurvey(testMember.getId(), response.surveyId());
        Survey savedSurvey = surveyCommandRepository.findById(response.surveyId()).orElse(null);

        // Then
        assertNull(savedSurvey);
    }

    @Test
    @DisplayName("수요조사를 찾을 수 없어 폼 삭제에 실패한다.")
    public void failRemoveSurveyWithNotFoundException() {

        assertThrows(SurveyNotFoundException.class, () -> {
            surveyCommandService.removeSurvey(testMember.getId(), 99999999L);
        });
    }

    @Test
    @DisplayName("작성자와 로그인 멤버가 같지않아  폼 삭제에 실패한다.")
    public void failRemoveSurveyWithNotWriterException() {
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

        // When & Then
        SurveyIdResponse response = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        surveyCommandRepository.flush();

        assertThrows(SurveyNotWriterException.class, () -> {
            surveyCommandService.removeSurvey(999999999L, response.surveyId());
        });
    }

}