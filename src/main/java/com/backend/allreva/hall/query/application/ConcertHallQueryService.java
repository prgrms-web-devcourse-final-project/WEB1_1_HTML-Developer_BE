package com.backend.allreva.hall.query.application;

import com.backend.allreva.hall.query.domain.ConcertHallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertHallQueryService {

    private final ConcertHallRepository concertHallRepository;


}
