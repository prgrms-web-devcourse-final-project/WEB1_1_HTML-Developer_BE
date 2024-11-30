package com.backend.allreva.rent.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.RentFormQueryService;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentFormSummaryResponse;
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
@RequestMapping("/api/v1/rent-forms")
public class RentFormViewControllerImpl implements RentFormViewController {

    private final RentFormQueryService rentFormQueryService;

    @GetMapping("/{id}")
    public Response<RentFormDetailResponse> getRentFormDetailById(
            @PathVariable final Long id
    ) {
        return Response.onSuccess(rentFormQueryService.getRentFormDetailById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}/deposit-account")
    public Response<DepositAccountResponse> getDepositAccountById(
            @PathVariable final Long id
    ) {
        return Response.onSuccess(rentFormQueryService.getDepositAccountById(id));
    }

    @GetMapping("/summary/list")
    public Response<List<RentFormSummaryResponse>> getRentFormSummaries(
            @RequestParam(name = "region", required = false) final Region region,
            @RequestParam(name = "sort", defaultValue = "LATEST") final SortType sortType,
            @RequestParam(name = "lastId", required = false) final Long lastId,
            @RequestParam(name = "lastEndDate", required = false) final LocalDate lastEndDate,
            @RequestParam(name = "pageSize", defaultValue = "10") @Min(10) final int pageSize
    ) {
        return Response.onSuccess(rentFormQueryService.getRentFormSummaries(region, sortType, lastEndDate, lastId, pageSize));
    }
}
