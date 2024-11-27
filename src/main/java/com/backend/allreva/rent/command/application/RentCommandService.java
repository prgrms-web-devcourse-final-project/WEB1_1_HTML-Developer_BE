package com.backend.allreva.rent.command.application;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.dto.RentFormRequest;
import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.RentFormRepository;
import com.backend.allreva.rent.exception.RentFormNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentCommandService {

    private final RentFormRepository rentFormRepository;

    @Transactional
    public void registerRentForm(
            final RentFormRequest rentFormRequest,
            final Member member
    ) {
        RentForm rentForm = rentFormRequest.toEntity(member.getId());
        rentFormRepository.save(rentForm);
    }

    @Transactional(readOnly = true)
    public RentForm getRentFormById(final Long id) {
        return rentFormRepository.findById(id).orElseThrow(RentFormNotFoundException::new);
    }
}
