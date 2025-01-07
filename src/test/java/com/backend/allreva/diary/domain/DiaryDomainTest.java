package com.backend.allreva.diary.domain;

import com.backend.allreva.common.model.Image;
import com.backend.allreva.diary.command.domain.ConcertDiary;
import com.backend.allreva.support.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class DiaryDomainTest extends IntegrationTestSupport {


    @DisplayName("공연 기록의 이미지를 수정하면 수정된 이미지로 대체된다")
    @Test
    void updateTest() {
        ConcertDiary diary = ConcertDiary.builder()
                .memberId(1L)
                .episode("episode1")
                .concertId(1L)
                .seatName("그냥 자리")
                .diaryDate(LocalDate.now())
                .content("내용")
                .build();
        Image image1 = new Image("image1");
        Image image2 = new Image("image2");
        List<Image> images = List.of(image1, image2);
        diary.addImages(images);

        Image image3 = new Image("image3");
        Image image4 = new Image("image4");
        List<Image> updatedImages = List.of(image3, image4);
        diary.update(
                1L,
                LocalDate.now(),
                "episode1",
                "내용",
                "그냥 자리",
                updatedImages
        );

        Assertions.assertThat(diary.getDiaryImages()).containsAll(updatedImages);
        Assertions.assertThat(diary.getDiaryImages()).doesNotContainAnyElementsOf(images);

    }
}
