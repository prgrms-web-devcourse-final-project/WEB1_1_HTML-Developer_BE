package com.backend.allreva.concert.command.domain;

import com.backend.allreva.concert.query.application.ConcertRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long>, ConcertRepositoryCustom {
    boolean existsByCodeConcertCode(String concertCode);
    Concert findByCodeConcertCode(String concertCode);
}
