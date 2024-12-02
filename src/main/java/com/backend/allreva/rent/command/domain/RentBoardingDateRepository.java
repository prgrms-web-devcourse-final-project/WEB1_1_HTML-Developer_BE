package com.backend.allreva.rent.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RentBoardingDateRepository extends JpaRepository<RentBoardingDate, Long> {
    @Modifying
    @Query("DELETE FROM RentBoardingDate rfbd WHERE rfbd.rent.id = :rentId")
    void deleteAllByRentId(Long rentId);
}
