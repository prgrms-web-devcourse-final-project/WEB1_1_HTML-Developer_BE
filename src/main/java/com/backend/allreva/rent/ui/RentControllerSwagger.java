package com.backend.allreva.rent.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.dto.RentIdRequest;
import com.backend.allreva.rent.command.application.dto.RentJoinApplyRequest;
import com.backend.allreva.rent.command.application.dto.RentJoinIdRequest;
import com.backend.allreva.rent.command.application.dto.RentJoinUpdateRequest;
import com.backend.allreva.rent.command.application.dto.RentRegisterRequest;
import com.backend.allreva.rent.command.application.dto.RentUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "차량 대절 폼 API", description = "차량 대절 폼 API")
public interface RentControllerSwagger {

    @Operation(summary = "차량 대절 생성 API", description = "차량 대절 폼을 생성합니다.")
    Response<Long> createRent(
            @RequestBody RentRegisterRequest rentRegisterRequest,
            @AuthMember Member member
    );

    @Operation(summary = "차량 대절 수정 API", description = "차량 대절 폼을 수정합니다.")
    Response<Void> updateRent(
            @RequestBody RentUpdateRequest rentUpdateRequest,
            @AuthMember Member member
    );

    @Operation(summary = "차량 대절 마감 API", description = "차량 대절 폼을 마감합니다.")
    Response<Void> closeRent(
            @RequestBody RentIdRequest rentIdRequest,
            @AuthMember Member member
    );

    @Operation(summary = "차량 대절 신청 API", description = "차량 대절 폼에 참여를 신청합니다.")
    Response<Long> applyRent(
            @RequestBody RentJoinApplyRequest rentJoinApplyRequest,
            @AuthMember Member member
    );

    @Operation(summary = "차량 대절 참여 수정 API", description = "차량 대절 폼의 참여 정보를 수정합니다.")
    Response<Void> updateRentJoin(
            @RequestBody RentJoinUpdateRequest rentJoinUpdateRequest,
            @AuthMember Member member
    );

    @Operation(summary = "차량 대절 참여 삭제 API", description = "차량 대절 폼의 참여 정보를 삭제합니다.")
    Response<Void> deleteRentJoin(
            @RequestBody RentJoinIdRequest rentJoinIdRequest,
            @AuthMember Member member
    );
}
