package com.backend.allreva.concert.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.search.query.domain.value.SortDirection;
import com.backend.allreva.concert.query.application.ConcertQueryService;
import com.backend.allreva.concert.query.application.dto.ConcertMainResponse;
import com.backend.allreva.concert.query.application.dto.ConcertDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts")
@RestController
@Validated
public class ConcertController {

    private final ConcertQueryService concertQueryService;

    @Operation(summary = "공연 상세 조회", description = "공연 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{concertId}")
    public Response<ConcertDetailResponse> findConcertDetail(
            @PathVariable("concertId") final Long concertId
    ) {
        ConcertDetailResponse detail = concertQueryService.findDetailById(concertId);
        return Response.onSuccess(detail);
    }

    @Operation(
            summary = "메인 화면 콘서트 api 입니다.",
            description = "searchAfter1, searchAfter2에 " +
                    "이전 SearchAfter에 있는 값들을 순서대로 넣어주어야 합니다."
    )
    @GetMapping("/list")
    public Response<ConcertMainResponse> getConcertMain(
            @RequestParam(defaultValue = "")
            final String region,
            @RequestParam(defaultValue = "DATE")
            final SortDirection sortDirection,
            @RequestParam(defaultValue = "4")
            final int PageSize,
            @RequestParam(required = false)
            final String searchAfter1,
            @RequestParam(required = false)
            final String searchAfter2

    ){
        List<Object> searchAfter = Stream.of(searchAfter1, searchAfter2)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        ConcertMainResponse concertMain = concertQueryService.getConcertMain(region, searchAfter, PageSize, sortDirection);
        return Response.onSuccess(concertMain);
    }


}
