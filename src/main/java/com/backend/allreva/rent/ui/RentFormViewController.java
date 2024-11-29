package com.backend.allreva.rent.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.rent.query.application.RentFormQueryService;
import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            summary = "차량 대절 폼 개설 API",
            description = """
                     차량 대절 폼을 개설합니다. \n\n
                     boardingDate 는 2024.11.30(토) 와 같은 형태로 주세요. \n\n
                     endDate는 2024-11-30 과 같은 형태로 주세요.
                    """)
    @GetMapping("/{id}")
    public ResponseEntity<Response<RentFormDetailResponse>> getRentFormDetailById(
            @PathVariable final Long id
    ) {
        return ResponseEntity.ok(
                Response.onSuccess(rentFormQueryService.getRentFormDetailById(id)));
    }
}
