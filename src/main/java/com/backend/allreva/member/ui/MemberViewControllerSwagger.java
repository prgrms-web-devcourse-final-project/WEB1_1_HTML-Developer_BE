package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.query.application.response.MemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "회원 정보 조회 API", description = "회원 정보 조회 API")
public interface MemberViewControllerSwagger {

    @Operation(
            summary = "회원 정보 조회",
            description = "회원 정보를 조회합니다."
    )
    Response<MemberDetail> getMemberDetail(
            @AuthMember Member member
    );
}
