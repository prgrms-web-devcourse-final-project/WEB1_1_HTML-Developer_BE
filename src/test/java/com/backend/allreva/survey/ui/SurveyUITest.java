package com.backend.allreva.survey.ui;

import com.backend.allreva.auth.filter.JwtAuthenticationFilter;
import com.backend.allreva.common.config.SecurityConfig;
import com.backend.allreva.support.ApiTestSupport;
import com.backend.allreva.support.WithCustomMockUser;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.survey_join.query.application.response.JoinSurveyResponse;
import com.backend.allreva.survey_join.command.application.request.JoinSurveyRequest;
import com.backend.allreva.survey.command.application.request.OpenSurveyRequest;
import com.backend.allreva.survey.command.application.request.SurveyIdRequest;
import com.backend.allreva.survey.command.application.request.UpdateSurveyRequest;
import com.backend.allreva.survey_join.command.domain.value.BoardingType;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey_join.query.application.SurveyJoinQueryService;
import com.backend.allreva.survey.query.application.SurveyQueryService;
import com.backend.allreva.survey.query.application.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {SurveyController.class},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {JwtAuthenticationFilter.class, SecurityConfig.class}
        )
)
@AutoConfigureMockMvc(addFilters = false)
class SurveyUITest extends ApiTestSupport {

    @MockBean
    private SurveyCommandService surveyCommandService;
    @MockBean
    private SurveyQueryService surveyQueryService;
    @MockBean
    private SurveyJoinQueryService surveyJoinQueryService;

    private static final String BASE_URI = "/api/v1/surveys";

    @Test
    @WithCustomMockUser
    @DisplayName("수요조사 개설에 성공한다.")
    void openSurvey() throws Exception {
        // Given
        OpenSurveyRequest request = new OpenSurveyRequest("하현상 콘서트: Elegy [서울]",
                1L,
                List.of(LocalDate.of(2030, 12, 1), LocalDate.of(2030, 12, 2)),
                "하현상",
                Region.서울,
                LocalDate.now(),
                25,
                "이틀 모두 운영합니다.");

        // Mocking
        doReturn(1L).when(surveyCommandService).openSurvey(any(), any());

        // When & Then
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(1L))
        ;
    }

    @Test
    @WithCustomMockUser
    @DisplayName("수요조사 수정에 성공한다.")
    void updateSurvey() throws Exception {
        // Given
        Long surveyId = 1L;
        UpdateSurveyRequest request = new UpdateSurveyRequest(surveyId,
                "하현상 콘서트: Elegy [서울]",
                List.of(LocalDate.of(2030, 12, 1), LocalDate.of(2030, 12, 2)),
                Region.서울,
                LocalDate.now(),
                25,
                "이틀 모두 운영합니다.");

        // Mocking
        doNothing().when(surveyCommandService).updateSurvey(any(), any());

        // When & Then
        mockMvc.perform(patch(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithCustomMockUser
    @DisplayName("수요조사 삭제에 성공한다.")
    void deleteSurvey() throws Exception {
        // Given
        SurveyIdRequest request = new SurveyIdRequest(1L);

        // Mocking
        doNothing().when(surveyCommandService).removeSurvey(any(), any());

        // When & Then
        mockMvc.perform(delete(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("수요조사 상세 조회에 성공한다.")
    void findSurveyDetail() throws Exception {
        // Given
        Long surveyId = 1L;
        SurveyDetailResponse response = new SurveyDetailResponse(surveyId,
                "하현상 콘서트 차대절 수요조사합니다.",
                List.of(new SurveyBoardingDateResponse(LocalDate.now(), 4)),
                "정보정보",
                false);

        // Mocking
        doReturn(response).when(surveyQueryService).findSurveyDetail(any());

        // When & Then
        mockMvc.perform(get(BASE_URI + "/{id}", surveyId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.surveyId").value(1L))
                .andExpect(jsonPath("$.result.title").value("하현상 콘서트 차대절 수요조사합니다."))
        ;
    }

    @Test
    @WithCustomMockUser
    @DisplayName("수요조사 응답에 성공한다.")
    void createSurveyJoin() throws Exception {
        // Given
        Long surveyId = 1L;
        JoinSurveyRequest request = new JoinSurveyRequest(surveyId,
                LocalDate.of(2030, 12, 1),
                BoardingType.DOWN,
                2,
                true
        );
        // Mocking
        doReturn(1L).when(surveyCommandService).createSurveyResponse(any(), any());

        // When & Then
        mockMvc.perform(post(BASE_URI + "/apply")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(1L))
        ;
    }

    @Test
    @WithCustomMockUser
    @DisplayName("수요조사 목록 조회에 성공한다.")
    void findSurveyList() throws Exception {
        // Given
        List<SurveySummaryResponse> responseList = new ArrayList<>();
        SurveySummaryResponse response = new SurveySummaryResponse(1L,
                "title",
                Region.경기,
                20,
                LocalDate.now());
        responseList.add(response);

        //param
        Region region = Region.서울;
        SortType sortType = SortType.LATEST;
        Long lastId = 1L;
        LocalDate lastEndDate = LocalDate.now();
        int pageSize = 10;

        // Mocking
        doReturn(responseList).when(surveyQueryService).findSurveyList(any(), any(), any(), any(), anyInt());

        // When & Then
        mockMvc.perform(get(BASE_URI + "/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("region", region.toString())
                        .param("sortType", sortType.toString())
                        .param("lastId", lastId.toString())
                        .param("lastEndDate", lastEndDate.toString())
                        .param("pageSize", String.valueOf(pageSize)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].surveyId").value(1L))
        ;
    }

    @Test
    @WithCustomMockUser
    @DisplayName("내가 개설한 수요조사 목록 조회에 성공한다.")
    public void getCreatedSurveyList() throws Exception {
        // Given
        List<CreatedSurveyResponse> responseList = new ArrayList<>();
        SurveyResponse surveyResponse = new SurveyResponse(1L,
                "하현상 콘서트 토요일 차대절 모집합니다.",
                LocalDate.of(2024, 11, 30),
                Region.서울,
                LocalDateTime.now(),
                LocalDate.of(2024, 11, 25),
                12,
                30
        );

        CreatedSurveyResponse response = new CreatedSurveyResponse(surveyResponse,
                2,
                2,
                2);
        responseList.add(response);

        int pageSize = 10;

        // Mocking
        doReturn(responseList).when(surveyJoinQueryService).getCreatedSurveyList(any(), any(), any(), anyInt());

        // When & Then
        mockMvc.perform(get(BASE_URI + "/member/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("pageSize", String.valueOf(pageSize)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].surveyResponse.surveyId").value(1L))
        ;
    }

    @Test
    @WithCustomMockUser
    @DisplayName("내가 참여한 수요조사 목록 조회에 성공한다.")
    public void getJoinSurveyList() throws Exception {
        // Given
        List<JoinSurveyResponse> responseList = new ArrayList<>();
        SurveyResponse surveyResponse = new SurveyResponse(1L,
                "하현상 콘서트 토요일 차대절 모집합니다.",
                LocalDate.of(2024, 11, 30),
                Region.서울,
                LocalDateTime.now(),
                LocalDate.of(2024, 11, 25),
                12,
                30
        );

        JoinSurveyResponse response = new JoinSurveyResponse(surveyResponse,
                1L,
                LocalDateTime.now(),
                BoardingType.ROUND,
                3);
        responseList.add(response);

        int pageSize = 10;

        // Mocking
        doReturn(responseList).when(surveyJoinQueryService).getJoinSurveyList(any(), any(), anyInt());

        // When & Then
        mockMvc.perform(get(BASE_URI + "/member/apply/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("pageSize", String.valueOf(pageSize)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].surveyJoinId").value(1L))
        ;
    }
}