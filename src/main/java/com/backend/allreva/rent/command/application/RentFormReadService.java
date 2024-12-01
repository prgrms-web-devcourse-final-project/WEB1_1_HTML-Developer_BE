package com.backend.allreva.rent.command.application;

import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.RentFormRepository;
import com.backend.allreva.rent.exception.RentFormNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentFormReadService {

    private final RentFormRepository rentFormRepository;

    public RentForm getRentFormById(final Long id) {
        return rentFormRepository.findById(id)
                .orElseThrow(RentFormNotFoundException::new);
    }

    public void checkRentFormById(final Long id) {
        if (!rentFormRepository.existsById(id)) {
            throw new RentFormNotFoundException();
        }
    }
}
