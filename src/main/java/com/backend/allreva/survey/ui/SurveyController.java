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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/surveys")
@Tag(name = "수요조사 API Controller")
public class SurveyController {
    private final SurveyCommandService surveyCommandService;

    @Operation(summary = "수요조사 개설 API", description = "수요조사를 개설합니다.")
    @PostMapping()
    public Response<SurveyIdResponse> openSurvey(@AuthMember Member member,
                                                 @Valid @RequestBody OpenSurveyRequest openSurveyRequest) {
        return Response.onSuccess(surveyCommandService.openSurvey(openSurveyRequest));
    }

}
