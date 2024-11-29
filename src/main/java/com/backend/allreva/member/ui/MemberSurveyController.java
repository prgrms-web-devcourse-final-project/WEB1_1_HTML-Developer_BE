package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.query.application.MemberSurveyQueryService;
import com.backend.allreva.member.query.application.dto.CreatedSurveyResponse;
import com.backend.allreva.member.query.application.dto.JoinSurveyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members/surveys")
@Tag(name = "마이페이지 수요조사 API Controller")
public class MemberSurveyController {
    private final MemberSurveyQueryService memberSurveyQueryService;

    @Operation(summary = "내가 개설한 수요조사 목록 조회 API", description =
            "첫페이지는 lastSurveyId 주지 않으셔도됩니다. 다음페이지부터는 마지막요소의 id 넣어주세요. \n" +
                    "default page size는 10입니다.")
    @GetMapping
    public Response<List<CreatedSurveyResponse>> getCreatedSurveyList(@AuthMember Member member,
                                                                      @RequestParam(value = "lastSurveyId", required = false) Long lastId,
                                                                      @Min(10) @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return Response.onSuccess(memberSurveyQueryService.getCreatedSurveyList(member.getId(), lastId, pageSize));
    }

    @Operation(summary = "내가 참여한 수요조사 목록 조회 API", description =
            "첫페이지는 lastSurveyJoinId 주지 않으셔도됩니다. 다음페이지부터는 마지막요소의 id 넣어주세요. \n" +
                    "default page size는 10입니다.")
    @GetMapping("/join")
    public Response<List<JoinSurveyResponse>> getJoinSurveyList(@AuthMember Member member,
                                                                @RequestParam(value = "lastSurveyJoinId", required = false) Long lastId,
                                                                @Min(10) @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return Response.onSuccess(memberSurveyQueryService.getJoinSurveyList(member.getId(), lastId, pageSize));
    }
}
