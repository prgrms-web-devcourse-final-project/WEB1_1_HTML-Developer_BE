package com.backend.allreva.diary.query.dto;

import com.backend.allreva.common.model.Image;

import java.time.LocalDate;
import java.util.Set;

public record DiaryDetailResponse(

        String concertTitle,
        Image concertPoster,

        LocalDate diaryDate,
        String episode,
        String seatName,
        Set<Image> diaryImages,
        String content
) {

}
