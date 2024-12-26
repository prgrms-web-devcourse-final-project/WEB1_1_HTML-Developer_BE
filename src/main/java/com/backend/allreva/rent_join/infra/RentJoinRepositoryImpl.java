package com.backend.allreva.rent_join.infra;

import com.backend.allreva.rent_join.command.domain.RentJoin;
import com.backend.allreva.rent_join.command.domain.RentJoinRepository;
import com.backend.allreva.rent_join.query.response.RentJoinResponse;
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

    @Override
    public List<RentJoinResponse> findRentJoin(final Long memberId) {
        return rentJoinDslRepository.findRentJoin(memberId);
    }
}
