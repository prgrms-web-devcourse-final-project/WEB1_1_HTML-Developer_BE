package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.application.MemberCommandFacade;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.application.dto.RefundAccountRequest;
import com.backend.allreva.member.command.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
public class MemberController implements MemberControllerSwagger {

    private final MemberCommandFacade memberCommandFacade;

    /**
     * oauth2 회원가입
     *
     * OAuth2 기본 이미지
     */
    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> registerMember(
            @AuthMember final Member member,
            @RequestPart final MemberInfoRequest memberInfoRequest,
            @RequestPart(value = "image", required = false) final MultipartFile image
    ) {
        memberCommandFacade.registerMember(memberInfoRequest, member, image);
        return ResponseEntity.noContent().build();
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
