package com.backend.allreva.rent_join.infra;

import com.backend.allreva.rent_join.command.domain.RentJoin;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentJoinJpaRepository extends JpaRepository<RentJoin, Long> {

    boolean existsByBoardingDateAndRentIdAndMemberId(LocalDate boardingDate, Long rentId, Long memberId);
}
