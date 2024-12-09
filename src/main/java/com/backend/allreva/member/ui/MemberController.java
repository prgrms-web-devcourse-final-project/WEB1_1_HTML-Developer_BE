package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.application.MemberCommandFacade;
import com.backend.allreva.member.command.application.request.MemberInfoRequest;
import com.backend.allreva.member.command.application.request.RefundAccountRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.query.application.MemberQueryService;
import com.backend.allreva.member.query.application.response.MemberDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
public class MemberController implements MemberControllerSwagger {

    private final MemberCommandFacade memberCommandFacade;
    private final MemberQueryService memberQueryService;

    @GetMapping
    public Response<MemberDetailResponse> getMemberDetail(
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

    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Void> registerMember(
            @AuthMember final Member member,
            @RequestPart final MemberInfoRequest memberInfoRequest,
            @RequestPart(value = "image", required = false) final MultipartFile image
    ) {
        memberCommandFacade.registerMember(memberInfoRequest, member, image);
        return Response.onSuccess();
    }

    @PatchMapping(path = "/info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Void> updateMemberInfo(
            @AuthMember final Member member,
            @RequestPart final MemberInfoRequest memberInfoRequest,
            @RequestPart(value = "image", required = false) final MultipartFile image
    ) {
        memberCommandFacade.updateMemberInfo(memberInfoRequest, member, image);
        return Response.onSuccess();
    }

    @PostMapping("/refund-account")
    public Response<Void> registerRefundAccount(
            @AuthMember final Member member,
            @RequestBody final RefundAccountRequest refundAccountRequest
    ) {
        memberCommandFacade.registerRefundAccount(refundAccountRequest, member);
        return Response.onSuccess();
    }

    @DeleteMapping("/refund-account")
    public Response<Void> deleteRefundAccount(
            @AuthMember final Member member
    ) {
        memberCommandFacade.deleteRefundAccount(member);
        return Response.onSuccess();
    }
}
