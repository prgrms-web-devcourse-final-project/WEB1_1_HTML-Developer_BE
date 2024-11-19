package com.allreva.concert.query.application;

import com.allreva.concert.query.domain.ConcertRepository;
import com.allreva.concert.query.application.dto.ConcertDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertQueryService {

    private final ConcertRepository concertRepository;

    public ConcertDetail findDetailById(Long concertId) {
        return concertRepository.findDetailById(concertId);
    }
}
