package com.backend.allreva.concert.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.concert.query.application.ConcertQueryService;
import com.backend.allreva.concert.query.application.dto.ConcertDetail;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/concerts")
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

}
