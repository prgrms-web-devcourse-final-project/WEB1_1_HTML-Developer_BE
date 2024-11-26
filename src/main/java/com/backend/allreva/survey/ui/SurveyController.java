package com.backend.allreva.survey.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.survey.command.application.dto.*;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.SurveyQueryService;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.dto.SurveySummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/surveys")
@Tag(name = "수요조사 API Controller")
public class SurveyController {
    private final SurveyCommandService surveyCommandService;
    private final SurveyQueryService surveyQueryService;

    @Operation(summary = "수요조사 개설 API", description = "수요조사를 개설합니다.")
    @PostMapping
    public Response<SurveyIdResponse> openSurvey(@AuthMember Member member,
                                                 @Valid @RequestBody OpenSurveyRequest openSurveyRequest) {
        return Response.onSuccess(surveyCommandService.openSurvey(member.getId(), openSurveyRequest));
    }

    @Operation(summary = "수요조사 수정 API", description = "수요조사를 수정합니다.")
    @PatchMapping
    public Response<Void> updateSurvey(@AuthMember Member member,
                                       @RequestParam(name = "surveyId") Long surveyId,
                                       @Valid @RequestBody UpdateSurveyRequest updateSurveyRequest) {
        surveyCommandService.updateSurvey(member.getId(), surveyId, updateSurveyRequest);
        return Response.onSuccess();
    }


    @Operation(summary = "수요조사 삭제 API", description = "수요조사를 삭제합니다.")
    @DeleteMapping
    public Response<Void> removeSurvey(@AuthMember Member member,
                                       @RequestParam(name = "surveyId") Long surveyId) {
        surveyCommandService.removeSurvey(member.getId(), surveyId);
        return Response.onSuccess();
    }

    @Operation(summary = "수요조사 상세 조회 API", description = "수요조사를 상세조회합니다.")
    @GetMapping("/{surveyId}")
    public Response<SurveyDetailResponse> findSurveyDetail(@PathVariable(name = "surveyId") Long surveyId) {
        return Response.onSuccess(surveyQueryService.findSurveyDetail(surveyId));
    }

    @Operation(summary = "수요조사 응답 제출 API", description = "수요조사에 대한 응답을 제출합니다.")
    @PostMapping("/{surveyId}/response")
    public Response<SurveyJoinIdResponse> createSurveyResponse(@AuthMember Member member,
                                                               @PathVariable(name = "surveyId") Long surveyId,
                                                               @Valid @RequestBody JoinSurveyRequest surveyJoinRequest) {
        return Response.onSuccess(surveyCommandService.createSurveyResponse(member.getId(), surveyId, surveyJoinRequest));
    }

    @Operation(summary = "수요조사 목록 조회 API", description = "수요조사를 목록으로 조회합니다. \n " +
            "최신순, 오래된 순으로 검색할경우 sort는 LATEST, OLDEST로 주시고, lastId에는 해당페이지의 마지막 id를 주시면 됩니다.\n" +
            "첫페이지일 경우 lastId 파라미터 주지 않으시면 됩니다. \n" +
            "마감순으로 검색할 경우 sort는 CLOSING으로 주시고, lastId는 해당 페이지의 마지막 id를 주세요. \n" +
            "마감순으로 검색할 경우 lastEndDate에는 해당 페이지의 마지막 id의 endDate를 필수로 주셔야합니다." +
            "마감순으로 검색할 경우 첫페이지일 경우에는 lastId, lastEndDate 둘다 주지 않으시면 됩니다. \n" +
            "pageSize는 파라미터로 전달하지 않으면 default값 10입니다. \n" +
            "region은 파라미터로 전달하지 않으면 전체 조회됩니다. sort도 파라미터로 전달하지 않으면 최신순으로 검색됩니다.")
    @GetMapping("/list")
    public Response<List<SurveySummaryResponse>> findSurveyList(@RequestParam(name = "region", required = false) Region region,
                                                                @RequestParam(name = "sort", defaultValue = "LATEST") SortType sortType,
                                                                @RequestParam(name = "lastId", required = false) Long lastId,
                                                                @RequestParam(name = "lastEndDate", required = false) LocalDate lastEndDate,
                                                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return Response.onSuccess(surveyQueryService.findSurveyList(region, sortType, lastId, lastEndDate, pageSize));
    }

}
