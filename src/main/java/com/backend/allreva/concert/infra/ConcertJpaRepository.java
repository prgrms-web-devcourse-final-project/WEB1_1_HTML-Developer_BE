package com.backend.allreva.concert.infra;

import com.backend.allreva.concert.command.domain.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertJpaRepository extends JpaRepository<Concert, Long>, ConcertDslRepository {
    boolean existsByCodeConcertCode(String concertCode);

    Concert findByCodeConcertCode(String concertCode);
}
