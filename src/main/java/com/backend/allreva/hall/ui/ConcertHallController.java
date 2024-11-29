package com.backend.allreva.hall.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.hall.query.application.ConcertHallQueryService;
import com.backend.allreva.hall.query.application.dto.ConcertHallDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/concert-halls")
@RestController
public class ConcertHallController {

    private final ConcertHallQueryService concertHallQueryService;

    @Operation(summary = "공연장 상세 조회", description = "공연장 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{hallCode}")
    public Response<ConcertHallDetail> findHallDetailByHallCode(
            @PathVariable("hallCode") String hallCode
    ) {
        ConcertHallDetail details = concertHallQueryService.findDetailByHallCode(hallCode);
        return Response.onSuccess(details);
    }
}
