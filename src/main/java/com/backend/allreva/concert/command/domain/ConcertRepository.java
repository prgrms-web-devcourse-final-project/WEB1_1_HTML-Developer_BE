package com.backend.allreva.concert.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    boolean existsByconcertCode(String concertCode);
    Concert findByconcertCode(String concertCode);
}
