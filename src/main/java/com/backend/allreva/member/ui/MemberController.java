package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.application.MemberCommandFacade;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.application.dto.RefundAccountRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.query.application.MemberQueryService;
import com.backend.allreva.member.query.application.dto.MemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
public class MemberController {

    private final MemberCommandFacade memberCommandFacade;
    private final MemberQueryService memberQueryService;

    @Operation(summary = "회원 프로필 조회", description = "회원 프로필 조회 API")
    @GetMapping
    public Response<MemberDetail> getMemberDetail(
            final @AuthMember Member member
    ) {
        return Response.onSuccess(memberQueryService.getById(member.getId()));
    }

    @Operation(summary = "회원 프로필 수정", description = "회원 프로필 수정 API")
    @PatchMapping("/info")
    public Response<Void> updateMemberInfo(
            final @AuthMember Member member,
            final @RequestBody MemberInfoRequest memberInfoRequest
    ) {
        memberCommandFacade.updateMemberInfo(memberInfoRequest, member);

        return Response.onSuccess();
    }

    @Operation(summary = "회원 환불 계좌 등록", description = "회원 환불 계좌 등록 API")
    @PostMapping("/refund-account")
    public Response<Void> registerRefundAccount(
            final @AuthMember Member member,
            final @RequestBody RefundAccountRequest refundAccountRequest
    ) {
        memberCommandFacade.registerRefundAccount(refundAccountRequest, member);

        return Response.onSuccess();
    }

    @Operation(summary = "회원 환불 계좌 삭제", description = "회원 환불 계좌 삭제 API")
    @DeleteMapping("/refund-account")
    public Response<Void> deleteRefundAccount(
            final @AuthMember Member member
    ) {
        memberCommandFacade.deleteRefundAccount(member);

        return Response.onSuccess();
    }
}
