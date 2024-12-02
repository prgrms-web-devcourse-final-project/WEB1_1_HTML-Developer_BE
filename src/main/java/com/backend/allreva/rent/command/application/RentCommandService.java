package com.backend.allreva.rent.command.application;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.dto.RentFormIdRequest;
import com.backend.allreva.rent.command.application.dto.RentFormRegisterRequest;
import com.backend.allreva.rent.command.application.dto.RentFormUpdateRequest;
import com.backend.allreva.rent.command.domain.RentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RentCommandService {

    private final RentFormReadService rentFormReadService;
    private final RentFormWriteService rentFormWriteService;

    public Long registerRentForm(
            final RentFormRegisterRequest rentFormRegisterRequest,
            final Member member
    ) {
        RentForm rentForm = rentFormRegisterRequest.toEntity(member.getId());
        RentForm savedRentForm = rentFormWriteService.saveRentForm(rentForm);
        return savedRentForm.getId();
    }

    public void updateRentForm(
            final RentFormUpdateRequest rentFormUpdateRequest,
            final Member member
    ) {
        RentForm rentForm = rentFormReadService.getRentFormById(rentFormUpdateRequest.rentFormId());

        rentForm.validateMine(member.getId());

        rentForm.updateRentForm(rentFormUpdateRequest);
        rentFormWriteService.updateRentFormBoardingDates(
                rentFormUpdateRequest.rentFormId(),
                rentFormUpdateRequest.toRentFormBoardingDates()
        );
        rentFormWriteService.saveRentForm(rentForm);
    }

    public void closeRentForm(
            final RentFormIdRequest rentFormId,
            final Member member
    ) {
        RentForm rentForm = rentFormReadService.getRentFormById(rentFormId.rentFormId());

        rentForm.validateMine(member.getId());
        rentForm.close();

        rentFormWriteService.saveRentForm(rentForm);
    }
}
