package com.backend.allreva.rent.command.application;

import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.RentFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentFormWriteService {

    private final RentFormRepository rentFormRepository;

    @Transactional
    public RentForm saveRentForm(RentForm rentForm) {
        return rentFormRepository.save(rentForm);
    }

    @Transactional
    public void deleteRentForm(RentForm rentForm) {
        rentFormRepository.delete(rentForm);
    }
}
