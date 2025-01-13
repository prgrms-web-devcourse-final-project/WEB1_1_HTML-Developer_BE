package com.backend.allreva.seatReview.Integration;

import com.backend.allreva.common.application.S3ImageService;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.seat_review.command.application.SeatReviewImageService;
import com.backend.allreva.seat_review.command.domain.SeatReviewImage;
import com.backend.allreva.seat_review.exception.SeatReviewImageSaveFailedException;
import com.backend.allreva.seat_review.infra.SeatReviewImageRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@Transactional
@Slf4j
public class SeatReviewImageServiceIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private SeatReviewImageService seatReviewImageService;
    @Autowired
    private SeatReviewImageRepository seatReviewImageRepository;

    @MockBean
    private S3ImageService s3ImageService;

    @BeforeEach
    void setup() {
        // S3ImageService.upload가 호출되면 간단히 "https://mock-url"을 반환하도록 설정
        BDDMockito.given(s3ImageService.upload(any(MultipartFile.class)))
                .willAnswer(invocation -> new Image("https://mock-url"));
    }

    @Test
    @DisplayName("좌석리뷰 이미지 저장 성공 테스트")
    void testSaveImages() {
        // given
        MultipartFile file1 = BDDMockito.mock(MultipartFile.class);
        MultipartFile file2 = BDDMockito.mock(MultipartFile.class);
        List<MultipartFile> files = Arrays.asList(file1, file2);

        Long seatReviewId = 999L;

        // when
        seatReviewImageService.saveImages(files, seatReviewId);

        // then
        // DB에서 seatReviewId로 조회해본다
        List<SeatReviewImage> imageList = seatReviewImageRepository.findBySeatReviewId(seatReviewId);
        assertThat(imageList).hasSize(2);

        log.info(imageList.toString());

        // 업로드된 url이 mock-url인지 확인
        assertThat(imageList.get(0).getUrl()).isEqualTo("https://mock-url");
        assertThat(imageList.get(1).getUrl()).isEqualTo("https://mock-url");
    }

    @Test
    @DisplayName("좌석리뷰 이미지 저장 예외 테스트")
    void testSaveImagesException() {
        // given
        MultipartFile file1 = BDDMockito.mock(MultipartFile.class);
        MultipartFile file2 = BDDMockito.mock(MultipartFile.class);
        List<MultipartFile> files = Arrays.asList(file1, file2);

        Long seatReviewId = 999L;

        // when
        // then
        assertThrows(SeatReviewImageSaveFailedException.class, () -> seatReviewImageService.saveImages(files, null));


    }
}
