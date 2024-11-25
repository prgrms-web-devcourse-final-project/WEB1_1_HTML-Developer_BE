package com.backend.allreva.survey.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.survey.command.application.dto.OpenSurveyRequest;
import com.backend.allreva.survey.command.application.dto.SurveyIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/surveys/form")
@Tag(name = "수요조사 API Controller")
public class SurveyController {
    private final SurveyCommandService surveyCommandService;

    @Operation(summary = "수요조사 개설 API", description = "수요조사를 개설합니다.")
    @PostMapping
    public Response<SurveyIdResponse> openSurvey(@AuthMember Member member,
                                                 @Valid @RequestBody OpenSurveyRequest openSurveyRequest) {
        return Response.onSuccess(surveyCommandService.openSurvey(member.getId(), openSurveyRequest));
    }


    @Operation(summary = "수요조사 삭제 API", description = "수요조사를 삭제합니다.")
    @DeleteMapping
    public Response<SurveyIdResponse> removeSurvey(@AuthMember Member member,
                                                   @RequestParam(name = "surveyId") Long surveyId) {
        surveyCommandService.removeSurvey(member.getId(), surveyId);
        return Response.onSuccess();
    }

}
