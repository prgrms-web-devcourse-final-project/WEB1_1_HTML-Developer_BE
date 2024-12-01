package com.backend.allreva.rent.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RentFormBoardingDateRepository extends JpaRepository<RentFormBoardingDate, Long> {
    @Modifying
    @Query("DELETE FROM RentFormBoardingDate rfbd WHERE rfbd.rentForm.id = :rentFormId")
    void deleteAllByRentFormId(Long rentFormId);
}
