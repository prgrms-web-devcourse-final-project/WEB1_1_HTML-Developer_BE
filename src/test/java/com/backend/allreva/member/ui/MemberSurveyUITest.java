package com.backend.allreva.member.ui;

import com.backend.allreva.auth.filter.JwtAuthenticationFilter;
import com.backend.allreva.common.config.SecurityConfig;
import com.backend.allreva.member.query.application.MemberSurveyQueryService;
import com.backend.allreva.member.query.application.dto.JoinSurveyResponse;
import com.backend.allreva.member.query.application.dto.SurveyResponse;
import com.backend.allreva.support.ApiTestSupport;
import com.backend.allreva.support.WithCustomMockUser;
import com.backend.allreva.survey.command.domain.value.BoardingType;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {MemberSurveyController.class},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {JwtAuthenticationFilter.class, SecurityConfig.class}
        )
)
@AutoConfigureMockMvc(addFilters = false)
public class MemberSurveyUITest extends ApiTestSupport {

    @MockBean
    private MemberSurveyQueryService memberSurveyQueryService;

    private static final String BASE_URI = "/api/v1/members/surveys";

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
        doReturn(responseList).when(memberSurveyQueryService).getJoinSurveyList(any(), any(), anyInt());

        // When & Then
        mockMvc.perform(get(BASE_URI + "/join")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("pageSize", String.valueOf(pageSize)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].surveyJoinId").value(1L))
        ;

    }
}
