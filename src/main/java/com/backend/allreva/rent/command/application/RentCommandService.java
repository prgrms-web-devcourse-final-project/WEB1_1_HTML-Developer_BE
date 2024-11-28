package com.backend.allreva.rent.command.application;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.dto.RentFormRegisterRequest;
import com.backend.allreva.rent.command.application.dto.RentFormUpdateRequest;
import com.backend.allreva.rent.command.domain.RentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentCommandService {

    private final RentFormReadService rentFormReadService;
    private final RentFormWriteService rentFormWriteService;

    @Transactional
    public RentForm registerRentForm(
            final RentFormRegisterRequest rentFormRegisterRequest,
            final Member member
    ) {
        RentForm rentForm = rentFormRegisterRequest.toEntity(member.getId());
        return rentFormWriteService.saveRentForm(rentForm);
    }

    @Transactional
    public RentForm updateRentForm(
            final RentFormUpdateRequest rentFormUpdateRequest,
            final Member member
    ) {
        RentForm rentForm = rentFormReadService.getRentFormById(rentFormUpdateRequest.rentFormId());

        rentForm.validateMine(member.getId());
        rentForm.updateRentForm(rentFormUpdateRequest);

        rentFormWriteService.saveRentForm(rentForm);
        return rentForm;
    }
}
