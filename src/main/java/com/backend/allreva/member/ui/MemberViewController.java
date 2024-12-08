package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.query.application.MemberQueryService;
import com.backend.allreva.member.query.application.dto.MemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
public class MemberViewController implements MemberViewControllerSwagger {

    private final MemberQueryService memberQueryService;

    @GetMapping
    public Response<MemberDetail> getMemberDetail(
            final @AuthMember Member member
    ) {
        return Response.onSuccess(memberQueryService.getById(member.getId()));
    }

    @GetMapping("/check-nickname")
    public Response<Boolean> isDuplicatedNickname(
            @RequestParam final String nickname
    ) {
        return Response.onSuccess(memberQueryService.isDuplicatedNickname(nickname).isDuplicated());
    }
}
