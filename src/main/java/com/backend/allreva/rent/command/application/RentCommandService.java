package com.backend.allreva.rent.command.application;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.dto.RentIdRequest;
import com.backend.allreva.rent.command.application.dto.RentRegisterRequest;
import com.backend.allreva.rent.command.application.dto.RentUpdateRequest;
import com.backend.allreva.rent.command.domain.Rent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RentCommandService {

    private final RentReadService rentReadService;
    private final RentWriteService rentWriteService;

    public Long registerRent(
            final RentRegisterRequest rentRegisterRequest,
            final Member member
    ) {
        Rent rent = rentRegisterRequest.toEntity(member.getId());
        Rent savedRent = rentWriteService.saveRent(rent);
        return savedRent.getId();
    }

    public void updateRent(
            final RentUpdateRequest rentUpdateRequest,
            final Member member
    ) {
        Rent rent = rentReadService.getRentById(rentUpdateRequest.rentId());

        rent.validateMine(member.getId());

        rent.updateRent(rentUpdateRequest);
        rentWriteService.updateRentBoardingDates(
                rentUpdateRequest.rentId(),
                rentUpdateRequest.toRentBoardingDates()
        );
        rentWriteService.saveRent(rent);
    }

    public void closeRent(
            final RentIdRequest rentId,
            final Member member
    ) {
        Rent rent = rentReadService.getRentById(rentId.rentFormId());

        rent.validateMine(member.getId());
        rent.close();

        rentWriteService.saveRent(rent);
    }
}
