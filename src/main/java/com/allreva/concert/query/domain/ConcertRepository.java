package com.allreva.concert.query.domain;

import com.allreva.concert.query.application.ConcertRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long>, ConcertRepositoryCustom {
}
