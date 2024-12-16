package com.backend.allreva.survey_join.ui;

import com.backend.allreva.auth.security.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.survey.query.application.response.CreatedSurveyResponse;
import com.backend.allreva.survey_join.command.application.SurveyJoinCommandService;
import com.backend.allreva.survey_join.command.application.request.JoinSurveyRequest;
import com.backend.allreva.survey_join.query.application.response.JoinSurveyResponse;
import com.backend.allreva.survey_join.query.application.SurveyJoinQueryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/surveys")
@RestController
public class SurveyJoinController {

    private final SurveyJoinCommandService surveyJoinCommandService;
    private final SurveyJoinQueryService surveyJoinQueryService;

    @Operation(summary = "수요조사 응답 제출 API", description = "수요조사에 대한 응답을 제출합니다.")
    @PostMapping("/apply")
    public Response<Long> createSurveyResponse(
            @AuthMember Member member,
            @Valid @RequestBody JoinSurveyRequest joinSurveyRequest
    ) {
        Long responseId = surveyJoinCommandService
                .createSurveyResponse(member.getId(), joinSurveyRequest);
        return Response.onSuccess(responseId);
    }


    @Operation(summary = "내가 개설한 수요조사 목록 조회 API", description =
            "첫페이지는 lastSurveyId 주지 않으셔도됩니다. 다음페이지부터는 마지막요소의 id 넣어주세요. \n" +
                    "default page size는 10입니다.")
    @GetMapping("/member/list")
    public Response<List<CreatedSurveyResponse>> getCreatedSurveyList(
            @AuthMember Member member,
            @RequestParam(value = "lastSurveyId", required = false) final Long lastId,
            @RequestParam(name = "lastBoardingDate", required = false) final LocalDate lastBoardingDate,
            @Min(10) @RequestParam(value = "pageSize", defaultValue = "10") final int pageSize
    ) {
        List<CreatedSurveyResponse> responses = surveyJoinQueryService
                .getCreatedSurveyList(member.getId(), lastId, lastBoardingDate, pageSize);
        return Response.onSuccess(responses);
    }

    @Operation(summary = "내가 참여한 수요조사 목록 조회 API", description =
            "첫페이지는 lastSurveyJoinId 주지 않으셔도됩니다. 다음페이지부터는 마지막요소의 id 넣어주세요. \n" +
                    "default page size는 10입니다.")
    @GetMapping("/member/apply/list")
    public Response<List<JoinSurveyResponse>> getJoinSurveyList(
            @AuthMember final Member member,
            @RequestParam(value = "lastSurveyJoinId", required = false) final Long lastId,
            @Min(10) @RequestParam(value = "pageSize", defaultValue = "10") final int pageSize
    ) {
        List<JoinSurveyResponse> responses = surveyJoinQueryService
                .getJoinSurveyList(member.getId(), lastId, pageSize);
        return Response.onSuccess(responses);
    }

}
