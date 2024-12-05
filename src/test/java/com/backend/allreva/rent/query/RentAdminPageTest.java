package com.backend.allreva.rent.query;

import static com.backend.allreva.rent.fixture.RentFixture.createRentFixture;
import static com.backend.allreva.rent.fixture.RentJoinFixture.createRentJoinFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.backend.allreva.rent.command.domain.RentJoinRepository;
import com.backend.allreva.rent.infra.RentJpaRepository;
import com.backend.allreva.rent.query.application.RentQueryService;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
class RentAdminPageTest extends IntegrationTestSupport {

    @Autowired
    private RentQueryService rentQueryService;
    @Autowired
    private RentJpaRepository rentJpaRepository;
    @Autowired
    private RentJoinRepository rentJoinRepository;

    @Test
    void 내가_등록한_차량_대절_리스트를_조회한다() {
        // given
        var registerId = 1L;
        var anotherRegisterId = 2L;
        var savedRent = rentJpaRepository.save(createRentFixture(registerId, 1L));
        rentJpaRepository.save(createRentFixture(anotherRegisterId, 2L));

        // when
        var rentAdminSummaries = rentQueryService.getRentAdminSummariesByMemberId(registerId);

        // then
        assertThat(rentAdminSummaries).hasSize(1);
        assertThat(rentAdminSummaries.get(0).maxRecruitmentCount()).isEqualTo(savedRent.getAdditionalInfo().getRecruitmentCount());
    }

    @Test
    void 차량_대절_관리_페이지에서_내가_등록한_차량_대절_상세_정보를_조회한다() {
        // given
        var registerId = 1L;
        var savedRent = rentJpaRepository.save(createRentFixture(registerId, 1L));
        var savedRentJoin = rentJoinRepository.save(createRentJoinFixture(savedRent.getId(), 2L));

        // when
        var rentAdminDetail = rentQueryService.getRentAdminDetail(registerId, savedRentJoin.getBoardingDate(), savedRent.getId());

        // then
        assertThat(rentAdminDetail).isNotNull();
        assertSoftly(softly -> {
            softly.assertThat(rentAdminDetail.getRentRoundCount()).isEqualTo(1);
            softly.assertThat(rentAdminDetail.getAdditionalDepositCount()).isEqualTo(1);
            softly.assertThat(rentAdminDetail.getRentJoinDetailResponses().get(0).rentJoinId()).isEqualTo(savedRentJoin.getId());
        });
    }
}
