package com.backend.allreva.rentJoin.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rentJoin.query.response.RentJoinSummaryResponse;
import com.backend.allreva.rentJoin.command.application.RentJoinCommandService;
import com.backend.allreva.rentJoin.command.application.request.RentJoinApplyRequest;
import com.backend.allreva.rentJoin.command.application.request.RentJoinIdRequest;
import com.backend.allreva.rentJoin.command.application.request.RentJoinUpdateRequest;
import com.backend.allreva.rentJoin.query.RentJoinQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RentJoinController {

    private final RentJoinCommandService rentJoinCommandService;
    private final RentJoinQueryService rentJoinQueryService;


    @PostMapping("/apply")
    public Response<Long> applyRent(
            @RequestBody final RentJoinApplyRequest rentJoinApplyRequest,
            @AuthMember final Member member
    ) {
        Long rentIdResponse = rentJoinCommandService.applyRent(rentJoinApplyRequest, member.getId());
        return Response.onSuccess(rentIdResponse);
    }

    @PatchMapping("/apply")
    public Response<Void> updateRentJoin(
            @RequestBody final RentJoinUpdateRequest rentJoinUpdateRequest,
            @AuthMember final Member member
    ) {
        rentJoinCommandService.updateRentJoin(rentJoinUpdateRequest, member.getId());
        return Response.onSuccess();
    }

    @DeleteMapping("/apply")
    public Response<Void> deleteRentJoin(
            @RequestBody final RentJoinIdRequest rentJoinIdRequest,
            @AuthMember final Member member
    ) {
        rentJoinCommandService.deleteRentJoin(rentJoinIdRequest, member.getId());
        return Response.onSuccess();
    }

    @GetMapping("/join/list")
    public Response<List<RentJoinSummaryResponse>> getRentJoinSummaries(
            @AuthMember Member member
    ) {
        return Response.onSuccess(rentJoinQueryService.getRentJoinSummariesByMemberId(member.getId()));
    }
}
