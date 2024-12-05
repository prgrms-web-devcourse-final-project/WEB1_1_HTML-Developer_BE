package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.application.dto.RefundAccountRequest;
import com.backend.allreva.member.command.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "회원 API", description = "회원 정보를 관리하는 API")
public interface MemberControllerSwagger {

    @Operation(summary = "회원 프로필 수정", description = "회원 프로필 수정 API")
    Response<Void> updateMemberInfo(
            @AuthMember Member member,
            @RequestBody MemberInfoRequest memberInfoRequest
    );

    @Operation(summary = "회원 환불 계좌 등록", description = "회원 환불 계좌 등록 API")
    Response<Void> registerRefundAccount(
            @AuthMember Member member,
            @RequestBody RefundAccountRequest refundAccountRequest
    );

    @Operation(summary = "회원 환불 계좌 삭제", description = "회원 환불 계좌 삭제 API")
    Response<Void> deleteRefundAccount(
            @AuthMember Member member
    );
}
