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
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "차량 대절 폼 API", description = "차량 대절 폼 API")
public interface RentControllerSwagger {

    Response<Long> createRent(
            @RequestBody RentRegisterRequest rentRegisterRequest,
            @AuthMember Member member
    );

    Response<Void> updateRent(
            @RequestBody RentUpdateRequest rentUpdateRequest,
            @AuthMember Member member
    );

    Response<Void> closeRent(
            @RequestBody RentIdRequest rentIdRequest,
            @AuthMember Member member
    );

    Response<Long> applyRent(
            @RequestBody RentJoinApplyRequest rentJoinApplyRequest,
            @AuthMember Member member
    );

    Response<Void> updateRentJoin(
            @RequestBody RentJoinUpdateRequest rentJoinUpdateRequest,
            @AuthMember Member member
    );

    Response<Void> deleteRentJoin(
            @RequestBody RentJoinIdRequest rentJoinIdRequest,
            @AuthMember Member member
    );
}
