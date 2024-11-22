package com.backend.allreva.concert.ui;

import com.backend.allreva.concert.command.application.ConcertCommandService;
import com.backend.allreva.concert.command.application.dto.AddConcertRequest;
import com.backend.allreva.concert.query.application.ConcertQueryService;
import com.backend.allreva.concert.query.application.dto.ConcertDetail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ConcertController {

    private final ConcertCommandService concertCommandService;
    private final ConcertQueryService concertQueryService;

    @PostMapping("/add")
    public ResponseEntity<Long> add(@RequestBody AddConcertRequest request) {
        Long savedId = concertCommandService.save(request);
        return ResponseEntity.ok(savedId);
    }


    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertDetail> findConcertDetail(
            @PathVariable("concertId") Long concertId
    ) {
        ConcertDetail detail = concertQueryService.findDetailById(concertId);
//        System.out.println("detail = " + detail.getPoster());
        return ResponseEntity.ok(detail);
    }
}
