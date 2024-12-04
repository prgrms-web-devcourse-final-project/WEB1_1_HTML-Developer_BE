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

@SuppressWarnings("NonAsciiCharacters")
//@Transactional
class RentAdminDetailReadTest extends IntegrationTestSupport {

    @Autowired
    private RentQueryService rentQueryService;
    @Autowired
    private RentJpaRepository rentJpaRepository;
    @Autowired
    private RentJoinRepository rentJoinRepository;

    @Test
    void 차량_대절_관리_페이지에서_내가_등록한_차량_대절_상세_정보를_조회한다() {
        var registerId = 1L;
        var savedRent = rentJpaRepository.save(createRentFixture(registerId, 1L));
        var savedRentJoin = rentJoinRepository.save(createRentJoinFixture(savedRent.getId(), 2L));

        var rentAdminDetail = rentQueryService.getRentAdminDetail(registerId, savedRentJoin.getBoardingDate(), savedRent.getId());

        assertThat(rentAdminDetail).isNotNull();
        assertSoftly(softly -> {
            softly.assertThat(rentAdminDetail.rentRoundCount()).isEqualTo(1);
            softly.assertThat(rentAdminDetail.additionalDepositCount()).isEqualTo(1);
            softly.assertThat(rentAdminDetail.rentJoinDetailResponses().get(0).depositorName())
                    .isEqualTo(savedRentJoin.getDepositor().getDepositorName());
        });
    }
}
