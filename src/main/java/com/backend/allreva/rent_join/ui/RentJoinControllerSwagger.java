package com.backend.allreva.rent_join.ui;

import com.backend.allreva.auth.security.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent_join.command.application.request.RentJoinApplyRequest;
import com.backend.allreva.rent_join.command.application.request.RentJoinIdRequest;
import com.backend.allreva.rent_join.command.application.request.RentJoinUpdateRequest;
import com.backend.allreva.rent_join.query.response.RentJoinSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "차량 대절 폼 신청 API", description = "차량 대절 폼 신청 API")
public interface RentJoinControllerSwagger {

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

    @Operation(
            summary = "내가 참여한 차 대절 리스트 조회 API",
            description = """
                    사용자가 참여한 차량 대절 폼의 요약된 정보를 리스트로 조회합니다.
                    """)
    Response<List<RentJoinSummaryResponse>> getRentJoinSummaries(
            Member member
    );
}
