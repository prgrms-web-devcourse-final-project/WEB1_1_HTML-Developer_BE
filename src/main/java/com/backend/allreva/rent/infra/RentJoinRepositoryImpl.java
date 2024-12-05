package com.backend.allreva.rent.infra;

import com.backend.allreva.rent.command.domain.RentJoin;
import com.backend.allreva.rent.command.domain.RentJoinRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RentJoinRepositoryImpl implements RentJoinRepository {

    private final RentJoinJpaRepository rentJoinJpaRepository;

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
}
