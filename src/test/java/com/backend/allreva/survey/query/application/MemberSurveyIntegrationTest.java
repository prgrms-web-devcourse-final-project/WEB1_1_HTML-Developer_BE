package com.backend.allreva.survey.query.application;

import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.surveyJoin.command.application.request.JoinSurveyRequest;
import com.backend.allreva.surveyJoin.command.domain.value.BoardingType;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.response.CreatedSurveyResponse;
import com.backend.allreva.surveyJoin.query.JoinSurveyResponse;
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
        Long firstId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.부산));
        JoinSurveyRequest joinRequest1 = new JoinSurveyRequest(firstId,
                LocalDate.of(2030, 12, 1), BoardingType.DOWN, 2, true
        );
        JoinSurveyRequest joinRequest2 = new JoinSurveyRequest(firstId,
                LocalDate.of(2030, 12, 1), BoardingType.UP, 2, true
        );

        surveyCommandService.createSurveyResponse(testMember.getId(), joinRequest1);
        surveyCommandService.createSurveyResponse(testMember.getId(), joinRequest2);

        // When
        List<CreatedSurveyResponse> responseList = memberSurveyQueryService.getCreatedSurveyList(testMember.getId(), null,null,10);

        // Then
        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertEquals(6, responseList.size());
    }

    @Test
    @DisplayName("내가 참여한 수요조사 목록 조회에 성공한다.")
    public void getJoinSurveyList() {
        // Given
        surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        Long secondSurveyId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        Long firstSurveyId = surveyCommandService.openSurvey(testMember.getId(), createOpenSurveyRequest(testConcert.getId(), LocalDate.now(), Region.서울));
        JoinSurveyRequest joinRequest1 = new JoinSurveyRequest(
                firstSurveyId, LocalDate.of(2030, 12, 1), BoardingType.DOWN, 2, true
        );
        JoinSurveyRequest joinRequest2 = new JoinSurveyRequest(
                firstSurveyId, LocalDate.of(2030, 12, 1), BoardingType.UP, 2, true
        );
        JoinSurveyRequest joinRequest3 = new JoinSurveyRequest(
                secondSurveyId, LocalDate.of(2030, 12, 1), BoardingType.UP, 2, true
        );


        surveyCommandService.createSurveyResponse(testMember.getId(), joinRequest1);
        surveyCommandService.createSurveyResponse(testMember.getId(), joinRequest2);
        Long firstId = surveyCommandService.createSurveyResponse(testMember.getId(), joinRequest3);


        // When
        List<JoinSurveyResponse> responseList = memberSurveyQueryService.getJoinSurveyList(testMember.getId(), null, 10);

        // Then
        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertEquals(3, responseList.size());
        assertEquals(firstId, responseList.get(0).getSurveyJoinId());
        assertEquals(LocalDate.of(2030, 12, 1), responseList.get(0).getSurveyResponse().getBoardingDate());
    }
}
