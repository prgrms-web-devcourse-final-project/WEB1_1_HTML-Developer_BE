package com.backend.allreva.concert.infra;


import com.backend.allreva.concert.query.application.dto.ConcertDetailResponse;

public interface ConcertDslRepository {
    ConcertDetailResponse findDetailById(Long concertId);

}
