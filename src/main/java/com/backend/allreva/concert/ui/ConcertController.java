package com.backend.allreva.concert.ui;

import com.backend.allreva.concert.query.application.ConcertQueryService;
import com.backend.allreva.concert.query.application.dto.ConcertDetail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ConcertController {

    private final ConcertQueryService concertQueryService;

    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertDetail> findConcertDetail(
            @PathVariable("concertId") Long concertId
    ) {
        ConcertDetail detail = concertQueryService.findDetailById(concertId);
        System.out.println("detail = " + detail.getPoster());
        return ResponseEntity.ok(detail);
    }
}
