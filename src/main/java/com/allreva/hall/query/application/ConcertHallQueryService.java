package com.allreva.hall.query.application;

import com.allreva.hall.query.domain.ConcertHallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertHallQueryService {

    private final ConcertHallRepository concertHallRepository;


}
