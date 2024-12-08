package com.backend.allreva.hall.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.hall.query.application.ConcertHallQueryService;
import com.backend.allreva.hall.query.application.dto.ConcertHallDetail;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/concert-halls")
@RestController
public class ConcertHallController {

    private final ConcertHallQueryService concertHallQueryService;

    @Operation(summary = "공연장 상세 조회", description = "공연장 상세 조회 API")
    @GetMapping("/{hallCode}")
    public Response<ConcertHallDetail> findHallDetailByHallCode(
            @PathVariable("hallCode") final String hallCode
    ) {
        ConcertHallDetail details = concertHallQueryService.findDetailByHallCode(hallCode);
        return Response.onSuccess(details);
    }
}
