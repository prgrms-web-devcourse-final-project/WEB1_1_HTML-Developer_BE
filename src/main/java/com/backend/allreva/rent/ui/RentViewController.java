package com.backend.allreva.rent.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.RentQueryService;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentJoinCountDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentJoinDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentJoinSummaryResponse;
import com.backend.allreva.rent.query.application.dto.RentSummaryResponse;
import com.backend.allreva.survey.query.application.dto.SortType;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rents")
public class RentViewController implements RentViewControllerSwagger {

    private final RentQueryService rentQueryService;

    @GetMapping("/{id}")
    public Response<RentDetailResponse> getRentDetailById(
            @PathVariable final Long id
    ) {
        return Response.onSuccess(rentQueryService.getRentDetailById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}/deposit-account")
    public Response<DepositAccountResponse> getDepositAccountById(
            @PathVariable final Long id
    ) {
        return Response.onSuccess(rentQueryService.getDepositAccountById(id));
    }

    @GetMapping("/list")
    public Response<List<RentSummaryResponse>> getRentSummaries(
            @RequestParam(name = "region", required = false) final Region region,
            @RequestParam(name = "sort", defaultValue = "LATEST") final SortType sortType,
            @RequestParam(name = "lastId", required = false) final Long lastId,
            @RequestParam(name = "lastEndDate", required = false) final LocalDate lastEndDate,
            @RequestParam(name = "pageSize", defaultValue = "10") @Min(10) final int pageSize
    ) {
        return Response.onSuccess(rentQueryService.getRentSummaries(region, sortType, lastEndDate, lastId, pageSize));
    }

    /**
     * 나의 차 대절 참여 현황 조회
     */
    @GetMapping("/member/list")
    public Response<List<RentJoinSummaryResponse>> getRentSummaries(
            @AuthMember Member member
    ) {
        return Response.onSuccess(rentQueryService.getRentJoinSummariesByMemberId(member.getId()));
    }

    /**
     * 관리자 입장 참여자 수 조회
     */
    @GetMapping("/{id}/apply/count")
    public Response<RentJoinCountDetailResponse> getRentJoinCountDetail(
            @AuthMember Member member,
            @PathVariable("id") final Long rentId
    ) {
        return Response.onSuccess(rentQueryService.getRentJoinCountDetailById(rentId));
    }

    /**
     * 관리자 입장 참여자 명단 리스트
     */
    @GetMapping("/{id}/apply/list")
    public Response<List<RentJoinDetailResponse>> getRentJoinDetailList(
            @PathVariable("id") final Long rentId
    ) {
        return Response.onSuccess(rentQueryService.getRentJoinDetailsById(rentId));
    }
}
