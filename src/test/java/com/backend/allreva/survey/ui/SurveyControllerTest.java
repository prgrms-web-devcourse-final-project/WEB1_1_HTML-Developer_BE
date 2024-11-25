package com.backend.allreva.survey.ui;

import com.backend.allreva.ControllerTestSupport;
import com.backend.allreva.WithCustomMockUser;
import com.backend.allreva.auth.filter.JwtAuthenticationFilter;
import com.backend.allreva.common.config.SecurityConfig;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.survey.command.application.dto.OpenSurveyRequest;
import com.backend.allreva.survey.command.application.dto.SurveyIdResponse;
import com.backend.allreva.survey.command.domain.value.Region;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class SurveyControllerTest extends ControllerTestSupport {

    @MockBean
    private SurveyCommandService surveyCommandService;

    private static final String BASE_URI = "/api/v1/surveys/form";

    @Test
    @WithCustomMockUser
    @DisplayName("설문조사 개설에 성공한다.")
    void openSurvey() throws Exception {
        // Given
        OpenSurveyRequest request = new OpenSurveyRequest("하현상 콘서트: Elegy [서울]",
                1L, List.of("2024.11.30(토)", "2024.12.01(일)"),
                "하현상", Region.SEOUL, LocalDate.now(),
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
    @DisplayName("설문조사 삭제에 성공한다.")
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

}