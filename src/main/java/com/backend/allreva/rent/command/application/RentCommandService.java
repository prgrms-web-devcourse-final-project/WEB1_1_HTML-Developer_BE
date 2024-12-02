package com.backend.allreva.rent.command.application;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.dto.RentIdRequest;
import com.backend.allreva.rent.command.application.dto.RentRegisterRequest;
import com.backend.allreva.rent.command.application.dto.RentUpdateRequest;
import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.command.domain.RentRepository;
import com.backend.allreva.rent.exception.RentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RentCommandService {

    private final RentRepository rentRepository;

    public Long registerRent(
            final RentRegisterRequest rentRegisterRequest,
            final Member member
    ) {
        Rent rent = rentRegisterRequest.toEntity(member.getId());
        Rent savedRent = rentRepository.save(rent);
        return savedRent.getId();
    }

    public void updateRent(
            final RentUpdateRequest rentUpdateRequest,
            final Member member
    ) {
        Rent rent = rentRepository.findById(rentUpdateRequest.rentId())
                .orElseThrow(RentNotFoundException::new);

        rent.validateMine(member.getId());

        rentRepository.deleteBoardingDateAllByRentId(rentUpdateRequest.rentId());
        rent.updateRent(rentUpdateRequest);
    }

    public void closeRent(
            final RentIdRequest rentIdRequest,
            final Member member
    ) {
        Rent rent = rentRepository.findById(rentIdRequest.rentId())
                .orElseThrow(RentNotFoundException::new);

        rent.validateMine(member.getId());
        rent.close();
    }
}
