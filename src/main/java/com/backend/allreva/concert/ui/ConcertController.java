package com.backend.allreva.concert.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.concert.command.domain.value.SortDirection;
import com.backend.allreva.concert.query.application.ConcertQueryService;
import com.backend.allreva.concert.query.application.dto.ConcertDetail;
import com.backend.allreva.concert.query.application.dto.ConcertMainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{concertId}")
    public Response<ConcertDetail> findConcertDetail(
            @PathVariable("concertId") Long concertId
    ) {
        ConcertDetail detail = concertQueryService.findDetailById(concertId);
        return Response.onSuccess(detail);
    }

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
