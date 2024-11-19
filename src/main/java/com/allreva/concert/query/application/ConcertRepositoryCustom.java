package com.allreva.concert.query.application;

import com.allreva.concert.query.application.dto.ConcertDetail;

public interface ConcertRepositoryCustom {
    ConcertDetail findDetailById(Long concertId);
}
