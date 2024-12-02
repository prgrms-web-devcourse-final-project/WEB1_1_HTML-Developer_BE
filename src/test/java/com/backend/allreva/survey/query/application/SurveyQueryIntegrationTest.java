package com.backend.allreva.survey.query.application;

import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import com.backend.allreva.survey.command.domain.SurveyBoardingDateCommandRepository;
import com.backend.allreva.survey.command.domain.SurveyCommandRepository;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.survey.command.application.dto.OpenSurveyRequest;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.dto.SurveySummaryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SurveyQueryIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SurveyCommandService surveyCommandService;
    @Autowired
    private SurveyQueryService surveyQueryService;
    @Autowired
    private SurveyCommandRepository surveyCommandRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private SurveyBoardingDateCommandRepository surveyBoardingDateCommandRepository;
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
        surveyCommandRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("수요조사 폼 상세 조회에 성공한다.")
    public void findSurveyDetail() {
        // Given
        OpenSurveyRequest openSurveyRequest = createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울);
        Long surveyId = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);

        // When
        SurveyDetailResponse detail = surveyQueryService.findSurveyDetail(surveyId);

        // Then
        assertNotNull(detail);
        assertEquals("하현상 콘서트: Elegy [서울] 수요조사 모집합니다.", detail.title());
        assertEquals(LocalDate.of(2030, 12, 1), detail.boardingDates().get(0));
        assertEquals(LocalDate.of(2030, 12, 2), detail.boardingDates().get(1));
        assertEquals(2, detail.boardingDates().size());
        assertThat(detail.boardingDates()).contains(LocalDate.of(2030, 12, 1));
    }

    @Test
    @DisplayName("수요조사 목록을 최신순으로 조회에 성공한다.")
    public void findSurveyList() {
        // Given
        surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        Long firstId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.부산));

        // When
        List<SurveySummaryResponse> responseList = surveyQueryService.findSurveyList(Region.서울, SortType.LATEST, null, null, 10);

        // Then
        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertThat(responseList).allMatch(response -> response.region().equals(Region.서울));
        assertEquals(0, responseList.get(0).participationCount());
        assertEquals(25, responseList.get(0).maxPassenger());
        assertEquals(firstId, responseList.get(0).surveyId());
    }

    @Test
    @DisplayName("수요조사 목록을 오래된순으로 조회에 성공한다.")
    public void findSurveyListOldest() {
        // Given
        Long lastId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.부산));

        // When
        List<SurveySummaryResponse> responseList = surveyQueryService.findSurveyList(Region.서울, SortType.OLDEST, null, null, 10);

        // Then
        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertThat(responseList).allMatch(response -> response.region().equals(Region.서울));
        assertEquals(lastId, responseList.get(0).surveyId());
    }

    @Test
    @DisplayName("수요조사 목록을 마감임박 순으로 조회에 성공한다.")
    public void findSurveyListClosing() {
        // Given
        Long secondId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now().plusDays(2), Region.서울)); // 두번째
        Long lastId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now().plusDays(2), Region.서울)); // 세번째
        Long firstId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now().plusDays(1), Region.서울)); //가장 첫번째

        // When
        List<SurveySummaryResponse> responseList = surveyQueryService.findSurveyList(Region.서울, SortType.CLOSING, null, null, 10);

        // Then
        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertEquals(firstId, responseList.get(0).surveyId());
        assertEquals(secondId, responseList.get(1).surveyId());
        assertEquals(lastId, responseList.get(2).surveyId());
    }

}
