package com.backend.allreva.survey.query.application;

import com.backend.allreva.IntegralTestSupport;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.member.command.application.MemberRepository;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.survey.command.application.SurveyCommandRepository;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.survey.command.application.dto.OpenSurveyRequest;
import com.backend.allreva.survey.command.application.dto.SurveyIdResponse;
import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SurveyQueryServiceTest extends IntegralTestSupport {

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
    @DisplayName("수요조사 폼 상세 조회에 성공한다.")
    public void findSurveyDetail() {
        // Given
        OpenSurveyRequest openSurveyRequest = new OpenSurveyRequest(
                "하현상 콘서트: Elegy [서울] 수요조사 모집합니다.",
                testConcert.getId(),
                of("2024.11.30(토)", "2024.12.01(일)"),
                "하현상",
                Region.서울,
                LocalDate.now(),
                25,
                "이틀 모두 운영합니다."
        );
        SurveyIdResponse response = surveyCommandService.openSurvey(testMember.getId(), openSurveyRequest);

        // When
        SurveyDetailResponse detail = surveyQueryService.findSurveyDetail(response.surveyId());

        // Then
        assertNotNull(detail);
        assertEquals("하현상 콘서트: Elegy [서울] 수요조사 모집합니다.", detail.getTitle());
        assertEquals("2024.11.30(토)", detail.getBoardingDate().get(0));
        assertEquals("2024.12.01(일)", detail.getBoardingDate().get(1));
        assertEquals(2, detail.getBoardingDate().size());
        assertThat(detail.getBoardingDate()).contains("2024.11.30(토)", "2024.12.01(일)");
    }
}
