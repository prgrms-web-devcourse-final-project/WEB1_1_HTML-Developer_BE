package com.backend.allreva.rent.infra;

import com.backend.allreva.rent.command.domain.RentBoardingDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RentBoardingDateJpaRepository extends JpaRepository<RentBoardingDate, Long> {
    @Modifying
    @Query("DELETE FROM RentBoardingDate rfbd WHERE rfbd.rent.id = :rentId")
    void deleteAllByRentId(Long rentId);
}
