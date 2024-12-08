package com.backend.allreva.rent_join.command.application;

import com.backend.allreva.rent.command.domain.RentRepository;
import com.backend.allreva.rent_join.exception.RentJoinNotFoundException;
import com.backend.allreva.rent.exception.RentNotFoundException;
import com.backend.allreva.rent_join.command.application.request.RentJoinApplyRequest;
import com.backend.allreva.rent_join.command.application.request.RentJoinIdRequest;
import com.backend.allreva.rent_join.command.application.request.RentJoinUpdateRequest;
import com.backend.allreva.rent_join.command.domain.RentJoin;
import com.backend.allreva.rent_join.command.domain.RentJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentJoinCommandService {

    private final RentRepository rentRepository;
    private final RentJoinRepository rentJoinRepository;

    public Long applyRent(
            final RentJoinApplyRequest rentJoinApplyRequest,
            final Long memberId
    ) {
        // 한방쿼리 작성??
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
