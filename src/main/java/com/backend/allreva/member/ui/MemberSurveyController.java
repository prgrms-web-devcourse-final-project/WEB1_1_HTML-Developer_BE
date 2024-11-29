package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.query.application.MemberSurveyQueryService;
import com.backend.allreva.member.query.application.dto.CreatedSurveyResponse;
import com.backend.allreva.member.query.application.dto.JoinSurveyResponse;
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
public class MemberSurveyController {
    private final MemberSurveyQueryService memberSurveyQueryService;

    @GetMapping
    public Response<List<CreatedSurveyResponse>> getCreatedSurveyList(@AuthMember Member member,
                                                                      @RequestParam(value = "lastSurveyId", required = false) Long lastId,
                                                                      @Min(10) @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return Response.onSuccess(memberSurveyQueryService.getCreatedSurveyList(member.getId(), lastId, pageSize));
    }

    @GetMapping("/join")
    public Response<List<JoinSurveyResponse>> getJoinSurveyList(@AuthMember Member member,
                                                                @RequestParam(value = "lastSurveyJoinId", required = false) Long lastId,
                                                                @Min(10) @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return Response.onSuccess(memberSurveyQueryService.getJoinSurveyList(member.getId(), lastId, pageSize));
    }
}
