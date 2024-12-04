package com.backend.allreva.rent.query;

import static com.backend.allreva.rent.fixture.RentFixture.createRentFixture;
import static org.assertj.core.api.Assertions.assertThat;

import com.backend.allreva.rent.infra.RentJpaRepository;
import com.backend.allreva.rent.query.application.RentQueryService;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
class RentAdminSummaryReadTest extends IntegrationTestSupport {

    @Autowired
    private RentQueryService rentQueryService;
    @Autowired
    private RentJpaRepository rentJpaRepository;

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
}
