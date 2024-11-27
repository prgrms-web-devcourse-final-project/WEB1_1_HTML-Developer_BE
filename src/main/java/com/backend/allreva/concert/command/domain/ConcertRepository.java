package com.backend.allreva.concert.command.domain;

import com.backend.allreva.concert.query.application.dto.ConcertDetailResponse;

public interface ConcertRepository {
    void save(Concert concert);

    ConcertDetailResponse findDetailById(Long concertId);

    void increaseViewCount(Long concertId);

    void deleteAll();

    boolean existsByConcertCode(String concertCode);

    Concert findByConcertCode(String concertCode);
}
