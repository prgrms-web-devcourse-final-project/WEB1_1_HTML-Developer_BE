package com.backend.allreva.rent.command.application;

import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.command.domain.RentBoardingDate;
import com.backend.allreva.rent.command.domain.RentBoardingDateRepository;
import com.backend.allreva.rent.command.domain.RentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RentWriteService {

    private final RentRepository rentRepository;
    private final RentBoardingDateRepository rentBoardingDateRepository;

    public Rent saveRent(final Rent rent) {
        return rentRepository.save(rent);
    }

    public List<RentBoardingDate> updateRentBoardingDates(
            final Long rentId,
            final List<RentBoardingDate> rentBoardingDates
    ) {
        rentBoardingDateRepository.deleteAllByRentId(rentId);
        // TODO: bulk insert
        return rentBoardingDateRepository.saveAll(rentBoardingDates);
    }

    public void deleteRent(final Rent rent) {
        rentRepository.delete(rent);
    }
}
