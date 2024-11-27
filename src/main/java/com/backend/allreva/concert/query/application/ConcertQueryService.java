package com.backend.allreva.concert.query.application;


import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.query.application.dto.ConcertDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ConcertQueryService {

    private final ConcertRepository concertRepository;

    public ConcertDetailResponse findDetailById(final Long concertId) {
        concertRepository.increaseViewCount(concertId);
        return concertRepository.findDetailById(concertId);
    }

}
