package com.backend.allreva.concert.query.application;

import com.backend.allreva.concert.query.application.dto.ConcertDetail;

public interface ConcertRepositoryCustom {
    ConcertDetail findDetailById(Long concertId);
}
