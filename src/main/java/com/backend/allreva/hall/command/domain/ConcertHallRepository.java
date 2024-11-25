package com.backend.allreva.hall.command.domain;

import com.backend.allreva.hall.infra.ConcertHallRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertHallRepository extends JpaRepository<ConcertHall, String>, ConcertHallRepositoryCustom {
}
