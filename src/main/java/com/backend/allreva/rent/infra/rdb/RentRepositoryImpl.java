package com.backend.allreva.rent.infra.rdb;

import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.command.domain.RentBoardingDate;
import com.backend.allreva.rent.command.domain.RentRepository;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.response.DepositAccountResponse;
import com.backend.allreva.rent.query.application.response.RentAdminSummaryResponse;
import com.backend.allreva.rent.query.application.response.RentDetailResponse;
import com.backend.allreva.rent.query.application.response.RentJoinCountResponse;
import com.backend.allreva.rent.query.application.response.RentJoinDetailResponse;
import com.backend.allreva.rent.query.application.response.RentSummaryResponse;
import com.backend.allreva.survey.query.application.response.SortType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RentRepositoryImpl implements RentRepository {

    private final RentJpaRepository rentJpaRepository;
    private final RentDslRepositoryImpl rentDslRepository;
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

    @Override
    public List<RentSummaryResponse> findRentSummaries(
            final Region region,
            final SortType sortType,
            final LocalDate lastEndDate,
            final Long lastId,
            final int pageSize
    ) {
        return rentDslRepository.findRentSummaries(region, sortType, lastEndDate, lastId, pageSize);
    }

    @Override
    public Optional<RentDetailResponse> findRentDetailById(final Long rentId) {
        return rentDslRepository.findRentDetailById(rentId);
    }

    @Override
    public Optional<DepositAccountResponse> findDepositAccountById(
            final Long rentId
    ) {
        return rentDslRepository.findDepositAccountById(rentId);
    }

    @Override
    public List<RentAdminSummaryResponse> findRentAdminSummaries(
            final Long memberId
    ) {
        return rentDslRepository.findRentAdminSummaries(memberId);
    }

    @Override
    public Optional<RentAdminSummaryResponse> findRentAdminSummary(
            final Long memberId,
            final LocalDate boardingDate,
            final Long rentId
    ) {
        return rentDslRepository.findRentAdminSummary(memberId, boardingDate, rentId);
    }

    @Override
    public Optional<RentJoinCountResponse> findRentJoinCount(
            final Long memberId,
            final LocalDate boardingDate,
            final Long rentId
    ) {
        return rentDslRepository.findRentJoinCount(memberId, boardingDate, rentId);
    }

    @Override
    public List<RentJoinDetailResponse> findRentJoinDetails(
            final Long memberId,
            final Long rentId,
            final LocalDate boardingDate
    ) {
        return rentDslRepository.findRentJoinDetails(memberId, rentId, boardingDate);
    }

    @Override
    public List<RentSummaryResponse> findRentMainSummaries() {
        return rentDslRepository.findRentMainSummaries();
    }
}
