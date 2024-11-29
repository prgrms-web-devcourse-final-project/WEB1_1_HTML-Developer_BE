package com.backend.allreva.rent.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "차량 대절 폼 조회 API", description = "차량 대절 폼 조회 API")
public interface RentFormViewController {

    @Operation(
            summary = "차량 대절 폼 상세 조회 API",
            description = """
                    차량 대절 폼을 상세 조회합니다.
                    boardingDate 는 2024.11.30(토) 와 같은 형태로 반환됩니다.
                    endDate는 2024-11-30 과 같은 형태로 반환됩니다.
                    """)
    ResponseEntity<Response<RentFormDetailResponse>> getRentFormDetailById(
            @PathVariable Long id
    );

    @Operation(
            summary = "입금 계좌 조회 API",
            description = """
                    입금 계좌를 조회합니다.
                    현재 사용자가 USER 권한보다 아래면 입금 계좌를 조회할 수 없습니다.
                    """)
    ResponseEntity<Response<DepositAccountResponse>> getDepositAccountById(
            @PathVariable Long id
    );
}
