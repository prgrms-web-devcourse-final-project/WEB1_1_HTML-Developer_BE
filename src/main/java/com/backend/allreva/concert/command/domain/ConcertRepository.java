package com.backend.allreva.concert.command.domain;

import com.backend.allreva.concert.query.application.response.ConcertDetailResponse;
import com.backend.allreva.concert.query.application.response.ConcertThumbnail;

import java.util.List;

public interface ConcertRepository {
    Concert save(Concert concert);

    ConcertDetailResponse findDetailById(Long concertId);

    void increaseViewCount(Long concertId);

    void deleteAllInBatch();

    boolean existsByConcertCode(String concertCode);

    Concert findByConcertCode(String concertCode);

    boolean existsById(Long concertId);

    List<ConcertThumbnail> getConcertMainThumbnails();

}
