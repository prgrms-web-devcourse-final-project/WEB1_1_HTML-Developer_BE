package com.backend.allreva.rentJoin.infra;

import com.backend.allreva.rentJoin.query.response.RentJoinSummaryResponse;
import com.backend.allreva.rentJoin.command.domain.RentJoin;
import com.backend.allreva.rentJoin.command.domain.RentJoinRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RentJoinRepositoryImpl implements RentJoinRepository {

    private final RentJoinJpaRepository rentJoinJpaRepository;
    private final RentJoinDslRepository rentJoinDslRepository;

    @Override
    public Optional<RentJoin> findById(final Long id) {
        return rentJoinJpaRepository.findById(id);
    }

    @Override
    public boolean existsById(final Long id) {
        return rentJoinJpaRepository.existsById(id);
    }

    @Override
    public RentJoin save(final RentJoin rentJoin) {
        return rentJoinJpaRepository.save(rentJoin);
    }

    @Override
    public void delete(final RentJoin rentJoin) {
        rentJoinJpaRepository.delete(rentJoin);
    }

    // rentJoinRepository.findRentJoinSummariesByMemberId(memberId);

    @Override
    public List<RentJoinSummaryResponse> findRentJoinSummariesByMemberId(Long memberId) {
        return rentJoinDslRepository.findRentJoinSummariesByMemberId(memberId);
    }
}
