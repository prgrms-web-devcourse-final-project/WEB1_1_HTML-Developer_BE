package com.backend.allreva.rent.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.rent.query.application.RentFormQueryService;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rent-form")
public class RentFormViewControllerImpl implements RentFormViewController {

    private final RentFormQueryService rentFormQueryService;

    @GetMapping("/{id}")
    public ResponseEntity<Response<RentFormDetailResponse>> getRentFormDetailById(
            @PathVariable final Long id
    ) {
        return ResponseEntity.ok(
                Response.onSuccess(rentFormQueryService.getRentFormDetailById(id)));
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}/deposit-account")
    public ResponseEntity<Response<DepositAccountResponse>> getDepositAccountById(
            @PathVariable final Long id
    ) {
        return ResponseEntity.ok(
                Response.onSuccess(rentFormQueryService.getDepositAccountById(id)));
    }
}
