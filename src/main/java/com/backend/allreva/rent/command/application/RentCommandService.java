package com.backend.allreva.rent.command.application;

import com.backend.allreva.common.event.Events;
import com.backend.allreva.rent.command.application.dto.RentIdRequest;
import com.backend.allreva.rent.command.application.dto.RentJoinApplyRequest;
import com.backend.allreva.rent.command.application.dto.RentJoinIdRequest;
import com.backend.allreva.rent.command.application.dto.RentJoinUpdateRequest;
import com.backend.allreva.rent.command.application.dto.RentRegisterRequest;
import com.backend.allreva.rent.command.application.dto.RentUpdateRequest;
import com.backend.allreva.rent.command.domain.*;
import com.backend.allreva.rent.exception.RentJoinNotFoundException;
import com.backend.allreva.rent.exception.RentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RentCommandService {

    private final RentRepository rentRepository;
    private final RentJoinRepository rentJoinRepository;

    public Long registerRent(
            final RentRegisterRequest rentRegisterRequest,
            final Long memberId
    ) {
        Rent rent = rentRegisterRequest.toEntity(memberId);
        Rent savedRent = rentRepository.save(rent);
        Events.raise(new RentSaveEvent(savedRent));
        return savedRent.getId();
    }

    public void updateRent(
            final RentUpdateRequest rentUpdateRequest,
            final Long memberId
    ) {
        Rent rent = rentRepository.findById(rentUpdateRequest.rentId())
                .orElseThrow(RentNotFoundException::new);

        rent.validateMine(memberId);

        rentRepository.deleteBoardingDateAllByRentId(rentUpdateRequest.rentId());
        rent.updateRent(rentUpdateRequest);
    }

    public void closeRent(
            final RentIdRequest rentIdRequest,
            final Long memberId
    ) {
        Rent rent = rentRepository.findById(rentIdRequest.rentId())
                .orElseThrow(RentNotFoundException::new);

        rent.validateMine(memberId);
        rent.close();
    }

    public Long applyRent(
            final RentJoinApplyRequest rentJoinApplyRequest,
            final Long memberId
    ) {
        if (!rentRepository.existsById(rentJoinApplyRequest.rentId())) {
            throw new RentNotFoundException();
        }

        RentJoin rentJoin = rentJoinApplyRequest.toEntity(memberId);

        RentJoin savedRentJoin = rentJoinRepository.save(rentJoin);
        return savedRentJoin.getId();
    }

    public void updateRentJoin(
            final RentJoinUpdateRequest rentJoinUpdateRequest,
            final Long memberId
    ) {
        RentJoin rentJoin = rentJoinRepository.findById(rentJoinUpdateRequest.rentJoinId())
                .orElseThrow(RentJoinNotFoundException::new);

        rentJoin.validateMine(memberId);
        rentJoin.updateRentJoin(rentJoinUpdateRequest);
    }

    public void deleteRentJoin(
            final RentJoinIdRequest rentJoinIdRequest,
            final Long memberId
    ) {
        RentJoin rentJoin = rentJoinRepository.findById(rentJoinIdRequest.rentJoinId())
                .orElseThrow(RentJoinNotFoundException::new);

        rentJoin.validateMine(memberId);
        rentJoinRepository.delete(rentJoin);
    }
}
