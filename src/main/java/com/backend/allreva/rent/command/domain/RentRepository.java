package com.backend.allreva.rent.command.domain;

import java.util.List;
import java.util.Optional;

public interface RentRepository {

    Optional<Rent> findById(Long id);

    boolean existsById(Long id);

    Rent save(Rent rent);

    List<RentBoardingDate> updateRentBoardingDates(Long rentId, List<RentBoardingDate> rentBoardingDates);

    void delete(final Rent rent);
}
