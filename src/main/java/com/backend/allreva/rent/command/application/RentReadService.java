package com.backend.allreva.rent.command.application;

import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.command.domain.RentRepository;
import com.backend.allreva.rent.exception.RentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentReadService {

    private final RentRepository rentRepository;

    public Rent getRentById(final Long id) {
        return rentRepository.findById(id)
                .orElseThrow(RentNotFoundException::new);
    }

    public void checkRentById(final Long id) {
        if (!rentRepository.existsById(id)) {
            throw new RentNotFoundException();
        }
    }
}
