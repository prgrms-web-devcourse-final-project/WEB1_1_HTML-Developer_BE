package com.backend.allreva.survey.ui;

import com.backend.allreva.ControllerTestSupport;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SurveyControllerTest extends ControllerTestSupport {

    @MockBean
    private SurveyCommandService surveyCommandService;

    @Test
    @DisplayName("설문조사 개설에 성공한다.")
    void openSurvey() throws Exception {
        // Given
        OpenSurveyRequest request = new OpenSurveyRequest(...); // 적절한 요청 생성
        SurveyIdResponse response = new SurveyIdResponse(1L); // 예상되는 응답

        // Mocking
        when(surveyCommandService.openSurvey(any())).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/surveys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.surveyId").value(1L));
    }

}