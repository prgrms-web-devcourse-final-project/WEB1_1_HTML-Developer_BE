package com.backend.allreva.rent.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.application.dto.RentFormIdRequest;
import com.backend.allreva.rent.command.application.dto.RentFormIdResponse;
import com.backend.allreva.rent.command.application.dto.RentFormRegisterRequest;
import com.backend.allreva.rent.command.application.dto.RentFormUpdateRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rent-form")
public class RentFormControllerImpl implements RentFormController {

    private final RentCommandService rentCommandService;

    @PostMapping
    public ResponseEntity<Response<RentFormIdResponse>> createRentForm(
            @RequestBody final RentFormRegisterRequest rentFormRegisterRequest,
            @AuthMember final Member member
    ) {
        RentFormIdResponse rentFormIdResponse = rentCommandService.registerRentForm(rentFormRegisterRequest, member);

        return ResponseEntity
                .created(URI.create("/api/v1/rent-form/" + rentFormIdResponse.rentFormId()))
                .body(Response.onSuccess(rentFormIdResponse));
    }

    @PatchMapping
    public ResponseEntity<Response<Void>> updateRentForm(
        @RequestBody final RentFormUpdateRequest rentFormUpdateRequest,
        @AuthMember final Member member
    ) {
        rentCommandService.updateRentForm(rentFormUpdateRequest, member);
        return ResponseEntity.ok(Response.onSuccess());
    }

    @PostMapping("/close")
    public ResponseEntity<Response<Void>> closeRentForm(
            @RequestBody final RentFormIdRequest request,
            @AuthMember final Member member
    ) {
        rentCommandService.closeRentForm(request, member);
        return ResponseEntity.ok(Response.onSuccess());
    }

}
