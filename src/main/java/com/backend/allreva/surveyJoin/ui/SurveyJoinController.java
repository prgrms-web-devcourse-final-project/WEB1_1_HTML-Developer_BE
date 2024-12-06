package com.backend.allreva.surveyJoin.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.survey.command.application.SurveyCommandService;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.exception.SurveyIllegalParameterException;
import com.backend.allreva.survey.query.application.MemberSurveyQueryService;
import com.backend.allreva.survey.query.application.SurveyQueryService;
import com.backend.allreva.survey.query.application.response.CreatedSurveyResponse;
import com.backend.allreva.survey.query.application.response.SortType;
import com.backend.allreva.survey.query.application.response.SurveySummaryResponse;
import com.backend.allreva.surveyJoin.command.application.SurveyJoinCommandService;
import com.backend.allreva.surveyJoin.command.application.request.JoinSurveyRequest;
import com.backend.allreva.surveyJoin.query.JoinSurveyResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SurveyJoinController {

    private final SurveyJoinCommandService surveyJoinCommandService;
    private final SurveyQueryService surveyQueryService;

    private final MemberSurveyQueryService memberSurveyQueryService;

    @Operation(summary = "수요조사 응답 제출 API", description = "수요조사에 대한 응답을 제출합니다.")
    @PostMapping("/apply")
    public Response<Long> createSurveyResponse(@AuthMember Member member,
                                               @Valid @RequestBody JoinSurveyRequest joinSurveyRequest) {
        Long responseId = surveyJoinCommandService
                .createSurveyResponse(member.getId(), joinSurveyRequest);
        return Response.onSuccess(responseId);
    }

    @Operation(
            summary = "수요조사 목록 조회 API",
            description = "수요조사 목록을 조회합니다.\n\n" +
                    "- <b>정렬 옵션</b>:\n" +
                    "  - 최신순 (<b>LATEST</b>): lastId를 마지막 항목의 ID로 전달. lastEndDate는 주지마세요.\n" +
                    "  - 오래된 순 (<b>OLDEST</b>): lastId를 마지막 항목의 ID로 전달. lastEndDate는 주지마세요.\n" +
                    "  - 마감 임박순 (<b>CLOSING</b>): 마지막 항목의 lastId와 lastEndDate를 전달하며, 둘 모두 필수입니다.\n\n" +
                    "- <b>첫 페이지 요청</b>: lastId와 lastEndDate를 전달하지 않으면 됩니다.\n" +
                    "- <b>기본값</b>:\n" +
                    "  - <b>sort</b>: 최신순 (LATEST)\n" +
                    "  - <b>pageSize</b>: 10\n" +
                    "  - <b>region</b>: 전체 조회"
    )
    @GetMapping("/list")
    public Response<List<SurveySummaryResponse>> findSurveyList(
            @RequestParam(name = "region", required = false) final Region region,
            @RequestParam(name = "sort", defaultValue = "LATEST") final SortType sortType,
            @RequestParam(name = "lastId", required = false) final Long lastId,
            @RequestParam(name = "lastEndDate", required = false) final LocalDate lastEndDate,
            @Min(10) @RequestParam(name = "pageSize", defaultValue = "10") final int pageSize
    ) {
        if (lastEndDate != null && lastId == null) {
            throw new SurveyIllegalParameterException();
        }

        List<SurveySummaryResponse> responses = surveyQueryService
                .findSurveyList(region, sortType, lastId, lastEndDate, pageSize);
        return Response.onSuccess(responses);
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
        List<CreatedSurveyResponse> responses = memberSurveyQueryService
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
        List<JoinSurveyResponse> responses = memberSurveyQueryService
                .getJoinSurveyList(member.getId(), lastId, pageSize);
        return Response.onSuccess(responses);
    }

}
