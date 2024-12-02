package com.backend.allreva.concert.command.domain;

import com.backend.allreva.concert.query.application.dto.ConcertDetailResponse;

public interface ConcertRepository {
    Concert save(Concert concert);

    ConcertDetailResponse findDetailById(Long concertId);

    void increaseViewCount(Long concertId);

    void deleteAllInBatch();

    boolean existsByConcertCode(String concertCode);

    Concert findByConcertCode(String concertCode);

    boolean existsById(Long concertId);
}
