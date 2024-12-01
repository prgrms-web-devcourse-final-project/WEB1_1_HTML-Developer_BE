package com.backend.allreva.survey.ui;

import com.backend.allreva.support.WithCustomMockUser;
import com.backend.allreva.auth.filter.JwtAuthenticationFilter;
import com.backend.allreva.common.config.SecurityConfig;
import com.backend.allreva.support.ApiTestSupport;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.survey.command.application.dto.*;
import com.backend.allreva.survey.command.domain.value.BoardingType;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.SurveyQueryService;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.dto.SurveySummaryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import java.time.LocalDate;
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
class SurveyControllerTest extends ApiTestSupport {

    @MockBean
    private SurveyCommandService surveyCommandService;
    @MockBean
    private SurveyQueryService surveyQueryService;

    private static final String BASE_URI = "/api/v1/surveys";

    @Test
    @WithCustomMockUser
    @DisplayName("수요조사 개설에 성공한다.")
    void openSurvey() throws Exception {
        // Given
        OpenSurveyRequest request = new OpenSurveyRequest("하현상 콘서트: Elegy [서울]",
                1L, List.of("2024.11.30(토)", "2024.12.01(일)"),
                "하현상", Region.서울, LocalDate.now(),
                25, "이틀 모두 운영합니다.");
        SurveyIdResponse response = new SurveyIdResponse(1L);

        // Mocking
        doReturn(response).when(surveyCommandService).openSurvey(any(), any());

        // When & Then
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.surveyId").value(1L))
        ;
    }

    @Test
    @WithCustomMockUser
    @DisplayName("수요조사 수정에 성공한다.")
    void updateSurvey() throws Exception {
        // Given
        Long surveyId = 1L;
        UpdateSurveyRequest request = new UpdateSurveyRequest("하현상 콘서트: Elegy [서울]",
                List.of("2024.11.30(토)", "2024.12.01(일)"),
                Region.서울, LocalDate.now(),
                25, "이틀 모두 운영합니다.");

        // Mocking
        doNothing().when(surveyCommandService).updateSurvey(any(), any(), any());

        // When & Then
        mockMvc.perform(patch(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("surveyId", String.valueOf(surveyId))
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
        Long surveyId = 1L;

        // Mocking
        doNothing().when(surveyCommandService).removeSurvey(any(), any());

        // When & Then
        mockMvc.perform(delete(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("surveyId", String.valueOf(surveyId)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("수요조사 상세 조회에 성공한다.")
    void findSurveyDetail() throws Exception {
        // Given
        Long surveyId = 1L;
        SurveyDetailResponse response = new SurveyDetailResponse(surveyId, "하현상 콘서트 차대절 수요조사합니다.", List.of(LocalDate.now()), "정보정보", false);

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
        JoinSurveyRequest request = new JoinSurveyRequest(
                "2024.11.30(토)", BoardingType.DOWN, 2, true
        );
        SurveyJoinIdResponse response = new SurveyJoinIdResponse(1L);

        // Mocking
        doReturn(response).when(surveyCommandService).createSurveyResponse(any(), any(), any());

        // When & Then
        mockMvc.perform(post(BASE_URI + "/{id}/response", surveyId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.surveyJoinId").value(1L))
        ;
    }

    @Test
    @WithCustomMockUser
    @DisplayName("수요조사 목록 조회에 성공한다.")
    void findSurveyList() throws Exception {
        // Given
        List<SurveySummaryResponse> responseList = new ArrayList<>();
        SurveySummaryResponse response = new SurveySummaryResponse(1L, "title", Region.경기, 20, 25, LocalDate.now());
        responseList.add(response);

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
}