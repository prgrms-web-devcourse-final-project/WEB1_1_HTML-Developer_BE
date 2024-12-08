package com.backend.allreva.survey.integration;

import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.exception.ConcertNotFoundException;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.survey.command.application.request.OpenSurveyRequest;
import com.backend.allreva.survey.command.application.request.SurveyIdRequest;
import com.backend.allreva.survey.command.application.request.UpdateSurveyRequest;
import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.survey.command.domain.SurveyBoardingDate;
import com.backend.allreva.survey.command.domain.SurveyBoardingDateCommandRepository;
import com.backend.allreva.survey.command.domain.SurveyRepository;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.exception.SurveyInvalidBoardingDateException;
import com.backend.allreva.survey.exception.SurveyNotFoundException;
import com.backend.allreva.survey.exception.SurveyNotWriterException;
import com.backend.allreva.survey_join.command.application.SurveyJoinCommandService;
import com.backend.allreva.survey_join.command.application.request.JoinSurveyRequest;
import com.backend.allreva.survey_join.command.domain.SurveyJoin;
import com.backend.allreva.survey_join.command.domain.SurveyJoinRepository;
import com.backend.allreva.survey_join.command.domain.value.BoardingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static java.util.List.of;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class SurveyCommandIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SurveyCommandService surveyCommandService;
    @Autowired
    private SurveyJoinCommandService surveyJoinCommandService;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyJoinRepository surveyJoinRepository;
    @Autowired
    private SurveyBoardingDateCommandRepository surveyBoardingDateCommandRepository;
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
        surveyBoardingDateCommandRepository.deleteAllInBatch();
        surveyRepository.deleteAllInBatch();
        surveyJoinRepository.deleteAllInBatch();
        concertRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("수요조사 폼 개설에 성공한다.")
    public void openSurvey() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);

        // When
        Long surveyId = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        Survey savedSurvey = surveyRepository.findById(surveyId).orElse(null);

        // Then
        assertNotNull(surveyId);
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
                of(LocalDate.of(2030, 12, 1),
                        LocalDate.of(2030, 12, 2)),
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
    public void removeSurvey() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);

        Long surveyId = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        surveyRepository.flush();

        // When
        surveyCommandService.removeSurvey(testMember.getId(), new SurveyIdRequest(surveyId));
        Survey savedSurvey = surveyRepository.findById(surveyId).orElse(null);
        List<SurveyBoardingDate> boardingDates = surveyBoardingDateCommandRepository.findAllBySurvey(savedSurvey);

        // Then
        assertNull(savedSurvey);
        assertEquals(0, boardingDates.size());
    }

    @Test
    @DisplayName("수요조사 폼 수정에 성공한다.")
    public void updateSurvey() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);

        Long surveyId = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        surveyRepository.flush();

        UpdateSurveyRequest updateSurveyRequest = new UpdateSurveyRequest(
                surveyId,
                "하현상 콘서트: Elegy [서울] 일요일 차대절 모집합니다.",
                List.of(LocalDate.of(2030, 12, 2)),
                Region.서울,
                LocalDate.now().plusDays(3),
                25,
                "일요일만 운영합니다."
        );

        // When
        surveyCommandService.updateSurvey(testMember.getId(), updateSurveyRequest);
        Survey savedSurvey = surveyRepository.findById(surveyId).orElse(null);
        List<SurveyBoardingDate> boardingDates = surveyBoardingDateCommandRepository.findAllBySurvey(savedSurvey);
        // Then
        assertNotNull(savedSurvey);
        assertEquals("하현상 콘서트: Elegy [서울] 일요일 차대절 모집합니다.", savedSurvey.getTitle());
        assertEquals(1, boardingDates.size());
        assertEquals(LocalDate.of(2030, 12, 2), boardingDates.get(0).getDate());


    }

    @Test
    @DisplayName("수요조사를 찾을 수 없어 폼 삭제에 실패한다.")
    public void failRemoveSurveyWithNotFoundException() {

        assertThrows(SurveyNotFoundException.class, () -> {
            surveyCommandService.removeSurvey(testMember.getId(), new SurveyIdRequest(99999999L));
        });
    }

    @Test
    @DisplayName("작성자와 로그인 멤버가 같지않아 폼 삭제에 실패한다.")
    public void failRemoveSurveyWithNotWriterException() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);

        Long surveyId = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);
        surveyRepository.flush();

        // When & Then
        assertThrows(SurveyNotWriterException.class, () -> {
            surveyCommandService.removeSurvey(999999999L, new SurveyIdRequest(surveyId));
        });
    }


    /**
     * SurveyJoin
     */

    @Test
    @DisplayName("수요조사 응답에 성공한다.")
    public void createSurveyResponse() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);
        Long surveyId = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);

        JoinSurveyRequest joinSurveyRequest = new JoinSurveyRequest(surveyId,
                LocalDate.of(2030, 12, 1), BoardingType.DOWN, 2, true
        );
        // When
        Long surveyJoinId = surveyJoinCommandService.createSurveyResponse(testMember.getId(), joinSurveyRequest);
        SurveyJoin savedSurveyJoin = surveyJoinRepository.findById(surveyJoinId).orElse(null);
        // Then
        assertNotNull(surveyJoinId);
        assertNotNull(savedSurveyJoin);
        assertEquals(surveyId, savedSurveyJoin.getSurveyId());
        assertEquals(LocalDate.of(2030, 12, 1), savedSurveyJoin.getBoardingDate());
    }

    @Test
    @DisplayName("올바르지 않은 날짜를 선택해 수요조사 응답에 실패한다.")
    public void failCreateSurveyResponse() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);
        Long surveyId = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);

        JoinSurveyRequest joinSurveyRequest = new JoinSurveyRequest(surveyId,
                LocalDate.of(2030, 12, 3), BoardingType.DOWN, 2, true
        );
        // When
        assertThrows(SurveyInvalidBoardingDateException.class, () -> {
            surveyJoinCommandService.createSurveyResponse(testMember.getId(), joinSurveyRequest);
        });
    }

}