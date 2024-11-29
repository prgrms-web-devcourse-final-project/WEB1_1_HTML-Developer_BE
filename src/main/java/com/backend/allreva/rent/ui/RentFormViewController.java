package com.backend.allreva.rent.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.rent.query.application.RentFormQueryService;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
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
public class RentFormViewController {

    private final RentFormQueryService rentFormQueryService;

    @Operation(
            summary = "차량 대절 폼 상세 조회 API",
            description = """
                    차량 대절 폼을 상세 조회합니다.
                    boardingDate 는 2024.11.30(토) 와 같은 형태로 반환됩니다.
                    endDate는 2024-11-30 과 같은 형태로 반환됩니다.
                    """)
    @GetMapping("/{id}")
    public ResponseEntity<Response<RentFormDetailResponse>> getRentFormDetailById(
            @PathVariable final Long id
    ) {
        return ResponseEntity.ok(
                Response.onSuccess(rentFormQueryService.getRentFormDetailById(id)));
    }

    @Operation(
            summary = "입금 계좌 조회 API",
            description = """
                    입금 계좌를 조회합니다.
                    현재 사용자가 USER 권한보다 아래면 입금 계좌를 조회할 수 없습니다.
                    """)
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}/deposit-account")
    public ResponseEntity<Response<DepositAccountResponse>> getDepositAccountById(
            @PathVariable final Long id
    ) {
        return ResponseEntity.ok(
                Response.onSuccess(rentFormQueryService.getDepositAccountById(id)));
    }
}
