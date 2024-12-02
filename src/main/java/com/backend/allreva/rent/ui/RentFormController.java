package com.backend.allreva.rent.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.dto.RentFormIdRequest;
import com.backend.allreva.rent.command.application.dto.RentFormIdResponse;
import com.backend.allreva.rent.command.application.dto.RentFormRegisterRequest;
import com.backend.allreva.rent.command.application.dto.RentFormUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "차량 대절 폼 API", description = "차량 대절 폼 API")
public interface RentFormController {

    Response<RentFormIdResponse> createRentForm(
            @RequestBody RentFormRegisterRequest rentFormRegisterRequest,
            @AuthMember Member member
    );

    Response<Void> updateRentForm(
            @RequestBody RentFormUpdateRequest rentFormUpdateRequest,
            @AuthMember Member member
    );

    Response<Void> closeRentForm(
            @RequestBody RentFormIdRequest request,
            @AuthMember Member member
    );
}
