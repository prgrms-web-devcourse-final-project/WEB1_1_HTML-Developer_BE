package com.backend.allreva.member.integration;

import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.member.query.application.MemberSurveyQueryService;
import com.backend.allreva.member.query.application.dto.CreatedSurveyResponse;
import com.backend.allreva.member.query.application.dto.JoinSurveyResponse;
import com.backend.allreva.support.IntegrationTestSupport;
import com.backend.allreva.survey.command.application.SurveyCommandRepository;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.survey.command.application.dto.JoinSurveyRequest;
import com.backend.allreva.survey.command.application.dto.SurveyIdResponse;
import com.backend.allreva.survey.command.application.dto.SurveyJoinIdResponse;
import com.backend.allreva.survey.command.domain.value.BoardingType;
import com.backend.allreva.survey.command.domain.value.Region;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MemberSurveyIntegrationTest extends IntegrationTestSupport {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SurveyCommandService surveyCommandService;
    @Autowired
    private MemberSurveyQueryService memberSurveyQueryService;
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
        memberRepository.deleteAllInBatch();
        concertRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("수요조사 개설 목록 조회에 성공한다.")
    public void getCreatedSurveyList() {
        // Given
        surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        SurveyIdResponse firstId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.부산));
        JoinSurveyRequest joinRequest1 = new JoinSurveyRequest(
                "2024.11.30(토)", BoardingType.DOWN, 2, true
        );
        JoinSurveyRequest joinRequest2 = new JoinSurveyRequest(
                "2024.11.30(토)", BoardingType.UP, 2, true
        );

        surveyCommandService.createSurveyResponse(testMember.getId(), firstId.surveyId(), joinRequest1);
        surveyCommandService.createSurveyResponse(testMember.getId(), firstId.surveyId(), joinRequest2);

        // When
        List<CreatedSurveyResponse> responseList = memberSurveyQueryService.getCreatedSurveyList(testMember.getId(), null, 10);

        // Then
        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertEquals(3, responseList.size());
        System.out.println(responseList.get(0).toString());
        System.out.println(responseList.get(1).toString());
        System.out.println(responseList.get(2).toString());


    }

    @Test
    @DisplayName("내가 참여한 수요조사 목록 조회에 성공한다.")
    public void getJoinSurveyList() {
        // Given
        surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        SurveyIdResponse secondSurveyId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        SurveyIdResponse firstSurveyId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        JoinSurveyRequest joinRequest1 = new JoinSurveyRequest(
                "2024.11.30(토)", BoardingType.DOWN, 2, true
        );
        JoinSurveyRequest joinRequest2 = new JoinSurveyRequest(
                "2024.11.30(토)", BoardingType.UP, 2, true
        );

        surveyCommandService.createSurveyResponse(testMember.getId(), firstSurveyId.surveyId(), joinRequest1);
        surveyCommandService.createSurveyResponse(testMember.getId(), firstSurveyId.surveyId(), joinRequest2);
        SurveyJoinIdResponse firstId = surveyCommandService.createSurveyResponse(testMember.getId(), secondSurveyId.surveyId(), joinRequest2);


        // When
        List<JoinSurveyResponse> responseList = memberSurveyQueryService.getJoinSurveyList(testMember.getId(), null, 10);

        // Then
        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertEquals(3, responseList.size());
        assertEquals(firstId.surveyJoinId(), responseList.get(0).getSurveyJoinId());
        assertEquals(LocalDate.of(2024, 11, 30), responseList.get(0).getSurveyResponse().getBoardingDate());
    }
}
