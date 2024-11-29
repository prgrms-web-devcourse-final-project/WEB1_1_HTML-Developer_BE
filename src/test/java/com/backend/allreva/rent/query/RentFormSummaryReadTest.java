package com.backend.allreva.rent.query;

import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.command.domain.value.Code;
import com.backend.allreva.concert.command.domain.value.ConcertInfo;
import com.backend.allreva.concert.command.domain.value.ConcertStatus;
import com.backend.allreva.concert.command.domain.value.DateInfo;
import com.backend.allreva.concert.command.domain.value.Seller;
import com.backend.allreva.hall.command.domain.ConcertHall;
import com.backend.allreva.hall.command.domain.ConcertHallRepository;
import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.RentFormRepository;
import com.backend.allreva.rent.command.domain.value.AdditionalInfo;
import com.backend.allreva.rent.command.domain.value.Bus;
import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rent.command.domain.value.DetailInfo;
import com.backend.allreva.rent.command.domain.value.OperationInfo;
import com.backend.allreva.rent.command.domain.value.Price;
import com.backend.allreva.rent.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.RentFormQueryService;
import com.backend.allreva.support.IntegrationTestSupport;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
class RentFormSummaryReadTest extends IntegrationTestSupport {

    @Autowired
    private RentFormQueryService rentFormQueryService;
    @Autowired
    private RentFormRepository rentFormRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private ConcertHallRepository concertHallRepository;

    @Test
    //@Transactional
    void 차량_대절_폼_리스트_조회를_성공한다() {
        // TODO: 차량 대절 폼 리스트 조회 구현 후 테스트 작성 예정
    }

    private RentForm createRentFormFixture(final Long concertId) {
        return RentForm.builder()
                .memberId(1L)
                .concertId(concertId)
                .detailInfo(DetailInfo.builder()
                        .image(new Image("imageUrl"))
                        .title("title")
                        .artistName("artistName")
                        .depositAccount("depositAccount")
                        .region(Region.서울)
                        .build())
                .operationInfo(OperationInfo.builder()
                        .boardingArea("boardingArea")
                        .boardingDates(List.of(LocalDate.of(2024, 9, 20)))
                        .upTime("09:00")
                        .downTime("23:00")
                        .bus(Bus.builder()
                                .busSize(BusSize.LARGE)
                                .busType(BusType.STANDARD)
                                .maxPassenger(28)
                                .build())
                        .price(Price.builder()
                                .roundPrice(30000)
                                .upTimePrice(30000)
                                .downTimePrice(30000)
                                .build())
                        .build())
                .additionalInfo(AdditionalInfo.builder()
                        .recruitmentCount(30)
                        .chatUrl("chatUrl")
                        .refundType(RefundType.BOTH)
                        .information("information")
                        .endDate(LocalDate.of(2024, 9, 13))
                        .build())
                .build();
    }

    private Concert createConcertFixture(final String hallCode) {
        return Concert.builder()
                .code(Code.builder()
                        .hallCode(hallCode)
                        .concertCode("concertCode")
                        .build())
                .concertInfo(new ConcertInfo("Sample Concert", "2024-12-01", ConcertStatus.IN_PROGRESS, "host",
                        new DateInfo(LocalDate.of(2024, 11, 30), LocalDate.of(2024, 12, 1), "timetable")))
                .poster(new Image("http://example.com/poster.jpg"))
                .detailImages(Set.of(new Image("http://example.com/detail1.jpg"), new Image("http://example.com/detail2.jpg")))
                .sellers(Set.of(new Seller("Sample Seller", "http://seller.com")))
                .build();
    }

    private ConcertHall createConcertHallFixture() {
        return ConcertHall.builder()
                .id("FC000001-01")
                .seatScale(10000)
                .convenienceInfo(ConvenienceInfo.builder()
                        .build())
                .name("Sample Hall")
                .location(Location.builder()
                        .address("Sample Address")
                        .latitude(37.123456)
                        .longitude(127.123456)
                        .build())
                .build();
    }
}
