package com.backend.allreva.concert.infra.repository;


import com.backend.allreva.concert.query.application.dto.ConcertDetailResponse;
import com.backend.allreva.concert.query.application.dto.ConcertThumbnail;

import java.util.List;

public interface ConcertDslRepository {
    ConcertDetailResponse findDetailById(Long concertId);

    List<ConcertThumbnail> getConcertMainThumbnails();

}
