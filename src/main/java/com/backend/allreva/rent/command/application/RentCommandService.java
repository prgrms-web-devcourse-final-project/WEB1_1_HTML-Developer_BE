package com.backend.allreva.rent.command.application;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.dto.RentFormIdResponse;
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
    public RentFormIdResponse registerRentForm(
            final RentFormRegisterRequest rentFormRegisterRequest,
            final Member member
    ) {
        RentForm rentForm = rentFormRegisterRequest.toEntity(member.getId());
        RentForm savedRentForm = rentFormWriteService.saveRentForm(rentForm);
        return new RentFormIdResponse(savedRentForm.getId());
    }

    @Transactional
    public void updateRentForm(
            final RentFormUpdateRequest rentFormUpdateRequest,
            final Member member
    ) {
        RentForm rentForm = rentFormReadService.getRentFormById(rentFormUpdateRequest.rentFormId());

        rentForm.validateMine(member.getId());
        rentForm.updateRentForm(rentFormUpdateRequest);

        rentFormWriteService.saveRentForm(rentForm);
    }
}
