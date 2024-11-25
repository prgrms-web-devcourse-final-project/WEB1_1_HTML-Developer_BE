package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.member.command.application.MemberCommandFacade;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.application.dto.RefundAccountRequest;
import com.backend.allreva.member.command.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
public class MemberController {

    private final MemberCommandFacade memberCommandFacade;

    @Operation(summary = "회원 프로필 수정", description = "회원 프로필 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/info")
    public ResponseEntity<Void> updateMemberInfo(
            @AuthMember Member member,
            @RequestBody MemberInfoRequest memberInfoRequest
    ) {
        memberCommandFacade.updateMemberInfo(memberInfoRequest, member);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 환불 계좌 등록", description = "회원 환불 계좌 등록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/refund-account")
    public ResponseEntity<Void> registerRefundAccount(
            @AuthMember Member member,
            @RequestBody RefundAccountRequest refundAccountRequest
    ) {
        memberCommandFacade.registerRefundAccount(refundAccountRequest, member);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 환불 계좌 삭제", description = "회원 환불 계좌 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/refund-account")
    public ResponseEntity<Void> deleteRefundAccount(
            @AuthMember Member member
    ) {
        memberCommandFacade.deleteRefundAccount(member);

        return ResponseEntity.noContent().build();
    }
}
