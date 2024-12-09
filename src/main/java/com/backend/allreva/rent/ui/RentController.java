package com.backend.allreva.rent.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.application.request.RentIdRequest;
import com.backend.allreva.rent.command.application.request.RentRegisterRequest;
import com.backend.allreva.rent.command.application.request.RentUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rents")
public class RentController implements RentControllerSwagger {

    private final RentCommandService rentCommandService;

    @PostMapping
    public Response<Long> createRent(
            @RequestBody final RentRegisterRequest rentRegisterRequest,
            @AuthMember final Member member
    ) {
        Long rentIdResponse = rentCommandService.registerRent(rentRegisterRequest, member.getId());

        return Response.onSuccess(rentIdResponse);
    }

    @PatchMapping
    public Response<Void> updateRent(
            @RequestBody final RentUpdateRequest rentUpdateRequest,
            @AuthMember final Member member
    ) {
        rentCommandService.updateRent(rentUpdateRequest, member.getId());
        return Response.onSuccess();
    }

    @PatchMapping("/close")
    public Response<Void> closeRent(
            @RequestBody final RentIdRequest rentIdRequest,
            @AuthMember final Member member
    ) {
        rentCommandService.closeRent(rentIdRequest, member.getId());
        return Response.onSuccess();
    }

}
