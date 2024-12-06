package com.backend.allreva.concert.infra.rdb;


import com.backend.allreva.concert.query.application.response.ConcertDetailResponse;
import com.backend.allreva.concert.query.application.response.ConcertThumbnail;

import java.util.List;

public interface ConcertDslRepository {
    ConcertDetailResponse findDetailById(Long concertId);

    List<ConcertThumbnail> getConcertMainThumbnails();

}
