package com.backend.allreva.rent.infra.rdb;

import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.command.domain.RentBoardingDate;
import com.backend.allreva.rent.command.domain.RentRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RentRepositoryImpl implements RentRepository {

    private final RentJpaRepository rentJpaRepository;
    private final RentBoardingDateJpaRepository rentBoardingDateJpaRepository;
    @Override
    public Optional<Rent> findById(final Long id) {
        return rentJpaRepository.findById(id);
    }

    @Override
    public boolean existsById(final Long id) {
        return rentJpaRepository.existsById(id);
    }

    @Override
    public Rent save(final Rent rent) {
        return rentJpaRepository.save(rent);
    }

    @Override
    public List<RentBoardingDate> updateRentBoardingDates(
            final Long rentId,
            final List<RentBoardingDate> rentBoardingDates
    ) {
        rentBoardingDateJpaRepository.deleteAllByRentId(rentId);
        // TODO: bulk insert
        return rentBoardingDateJpaRepository.saveAll(rentBoardingDates);
    }

    @Override
    public void deleteBoardingDateAllByRentId(final Long rentId) {
        rentBoardingDateJpaRepository.deleteAllByRentId(rentId);
    }

    @Override
    public void delete(final Rent rent) {
        rentJpaRepository.delete(rent);
    }
}
