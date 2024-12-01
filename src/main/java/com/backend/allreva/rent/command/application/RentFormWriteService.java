package com.backend.allreva.rent.command.application;

import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.RentFormBoardingDate;
import com.backend.allreva.rent.command.domain.RentFormBoardingDateRepository;
import com.backend.allreva.rent.command.domain.RentFormRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RentFormWriteService {

    private final RentFormRepository rentFormRepository;
    private final RentFormBoardingDateRepository rentFormBoardingDateRepository;

    public RentForm saveRentForm(final RentForm rentForm) {
        return rentFormRepository.save(rentForm);
    }

    public List<RentFormBoardingDate> updateRentFormBoardingDates(
            final Long rentFormId,
            final List<RentFormBoardingDate> rentFormBoardingDates
    ) {
        rentFormBoardingDateRepository.deleteAllByRentFormId(rentFormId);
        // TODO: bulk insert
        return rentFormBoardingDateRepository.saveAll(rentFormBoardingDates);
    }

    public void deleteRentForm(final RentForm rentForm) {
        rentFormRepository.delete(rentForm);
    }
}
