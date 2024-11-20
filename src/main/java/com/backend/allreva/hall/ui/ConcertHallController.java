package com.backend.allreva.hall.ui;

import com.backend.allreva.hall.query.application.ConcertHallQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ConcertHallController {

    private final ConcertHallQueryService concertHallQueryService;
}
