package com.backend.allreva.concert.infra.rdb;

import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.infra.dto.ConcertDateInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConcertJpaRepository extends JpaRepository<Concert, Long>, ConcertDslRepository {
    boolean existsByCodeConcertCode(String concertCode);

    Concert findByCodeConcertCode(String concertCode);

    @Query("SELECT c.concertInfo.dateInfo.startDate AS startDate, c.concertInfo.dateInfo.endDate AS endDate FROM Concert c WHERE c.id = :concertId")
    Optional<ConcertDateInfoResponse> findStartDateAndEndDateById(@Param("concertId") Long concertId);
}
