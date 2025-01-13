package com.backend.allreva.seatReview.Integration;

import com.backend.allreva.common.application.S3ImageService;
import com.backend.allreva.common.event.EventEntryRepository;
import com.backend.allreva.common.event.deadletter.DeadLetterHandler;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.seat_review.command.application.SeatReviewImageEvent;
import com.backend.allreva.seat_review.command.application.SeatReviewImageEventHandler;
import com.backend.allreva.seat_review.command.domain.SeatReviewImage;
import com.backend.allreva.seat_review.infra.SeatReviewImageRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@Transactional
@Slf4j
class SeatReviewImageEventHandlerIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private SeatReviewImageEventHandler eventHandler;

    @Autowired
    private SeatReviewImageRepository seatReviewImageRepository;

    @Autowired
    private EventEntryRepository eventEntryRepository;

    @Autowired
    private DeadLetterHandler deadLetterHandler;

    @MockBean
    private S3ImageService s3ImageService;

    @BeforeEach
    void setUp() {
        // 정상 업로드 시 "https://mock-url"을 반환하도록 Mock 설정
        BDDMockito.given(s3ImageService.upload(any(MultipartFile.class)))
                .willAnswer(inv -> new Image("https://mock-url"));
    }

    @Test
    @DisplayName("정상 케이스 - SeatReviewImageEvent가 잘 처리되어 DB에 정보가 저장되는지 확인")
    void testOnMessage_success() {
        // given
        Long seatReviewId = 100L;
        MultipartFile file1 = BDDMockito.mock(MultipartFile.class);
        MultipartFile file2 = BDDMockito.mock(MultipartFile.class);

        SeatReviewImageEvent event = new SeatReviewImageEvent(seatReviewId, List.of(file1, file2));
        // (참고) event.getTimestamp()는 super()에서 System.currentTimeMillis()로 자동 설정

        // when
        eventHandler.onMessage(event);

        // then
        // DB에 seatReviewId로 저장된 엔티티 존재하는지 확인
        List<SeatReviewImage> found = seatReviewImageRepository.findBySeatReviewId(seatReviewId);
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getUrl()).isEqualTo("https://mock-url");
        assertThat(found.get(1).getUrl()).isEqualTo("https://mock-url");
    }

}
