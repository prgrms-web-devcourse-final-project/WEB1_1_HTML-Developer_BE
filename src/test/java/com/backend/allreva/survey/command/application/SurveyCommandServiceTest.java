package com.backend.allreva.survey.command.application;

import com.backend.allreva.IntegralTestSupport;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.command.domain.exception.ConcertNotFoundException;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.survey.command.application.dto.*;
import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.survey.command.domain.SurveyJoin;
import com.backend.allreva.survey.command.domain.value.BoardingType;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.exception.SurveyNotFoundException;
import com.backend.allreva.survey.exception.SurveyNotWriterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
    private SurveyJoinCommandRepository surveyJoinCommandRepository;
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
        memberRepository.deleteAllInBatch();
        surveyCommandRepository.deleteAllInBatch();
        surveyJoinCommandRepository.deleteAllInBatch();
        concertRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("수요조사 폼 개설에 성공한다.")
    public void openSurvey() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);

        // When
        SurveyIdResponse response = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        Survey savedSurvey = surveyCommandRepository.findById(response.surveyId()).orElse(null);

        // Then
        assertNotNull(response);
        assertNotNull(savedSurvey);
        assertEquals("하현상 콘서트: Elegy [서울] 수요조사 모집합니다.", savedSurvey.getTitle());
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
                Region.서울,
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
    @Transactional //softDelete로 인해 tearDown으로 삭제되지 않음
    public void removeSurvey() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);

        SurveyIdResponse response = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        surveyCommandRepository.flush();

        // When
        surveyCommandService.removeSurvey(testMember.getId(), response.surveyId());
        Survey savedSurvey = surveyCommandRepository.findById(response.surveyId()).orElse(null);

        // Then
        assertNull(savedSurvey);
    }

    @Test
    @DisplayName("수요조사 폼 수정에 성공한다.")
    public void updateSurvey() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);

        UpdateSurveyRequest updateSurveyRequest = new UpdateSurveyRequest(
                "하현상 콘서트: Elegy [서울] 일요일 차대절 모집합니다.",
                List.of("2024.12.01(일)"),
                Region.서울,
                LocalDate.now().plusDays(3),
                25,
                "일요일만 운영합니다."
        );

        SurveyIdResponse response = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        surveyCommandRepository.flush();

        // When
        surveyCommandService.updateSurvey(testMember.getId(), response.surveyId(), updateSurveyRequest);
        Survey savedSurvey = surveyCommandRepository.findById(response.surveyId()).orElse(null);

        // Then
        assertNotNull(savedSurvey);
        assertEquals("하현상 콘서트: Elegy [서울] 일요일 차대절 모집합니다.", savedSurvey.getTitle());
        assertEquals(1, savedSurvey.getBoardingDate().size());
        assertEquals(LocalDate.of(2024,12,1), savedSurvey.getBoardingDate().get(0));


    }

    @Test
    @DisplayName("수요조사를 찾을 수 없어 폼 삭제에 실패한다.")
    public void failRemoveSurveyWithNotFoundException() {

        assertThrows(SurveyNotFoundException.class, () -> {
            surveyCommandService.removeSurvey(testMember.getId(), 99999999L);
        });
    }

    @Test
    @DisplayName("작성자와 로그인 멤버가 같지않아 폼 삭제에 실패한다.")
    public void failRemoveSurveyWithNotWriterException() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);

        // When & Then
        SurveyIdResponse response = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        surveyCommandRepository.flush();

        assertThrows(SurveyNotWriterException.class, () -> {
            surveyCommandService.removeSurvey(999999999L, response.surveyId());
        });
    }

    @Test
    @DisplayName("수요조사 응답에 성공한다.")
    public void createSurveyResponse() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);

        JoinSurveyRequest joinSurveyRequest = new JoinSurveyRequest(
                "2024.11.30(토)", BoardingType.DOWN, 2, true
        );

        SurveyIdResponse response = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);

        // When
        Long surveyJoinId = surveyCommandService.createSurveyResponse(testMember.getId(), response.surveyId(), joinSurveyRequest).surveyJoinId();
        SurveyJoin savedSurveyJoin = surveyJoinCommandRepository.findById(surveyJoinId).orElse(null);
        // Then
        assertNotNull(surveyJoinId);
        assertNotNull(savedSurveyJoin);
        assertEquals(response.surveyId(), savedSurveyJoin.getSurveyId());
        assertEquals(LocalDate.of(2024,11,30), savedSurveyJoin.getBoardingDate());
    }

}