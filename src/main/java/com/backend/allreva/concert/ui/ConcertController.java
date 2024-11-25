package com.backend.allreva.concert.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.concert.command.domain.value.SortDirection;
import com.backend.allreva.concert.query.application.ConcertQueryService;
import com.backend.allreva.concert.query.application.dto.ConcertDetail;
import com.backend.allreva.concert.query.application.dto.ConcertMainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts")
@RestController
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
            @RequestParam final String region,
            @RequestParam(defaultValue = "ASC") final SortDirection sortDirection,
            @RequestParam final int PageSize,
            @RequestParam final List<Object> searchAfter
    ){
        ConcertMainResponse concertMain = concertQueryService.getConcertMain(region, searchAfter, PageSize, sortDirection);
        return Response.onSuccess(concertMain);
    }


}
