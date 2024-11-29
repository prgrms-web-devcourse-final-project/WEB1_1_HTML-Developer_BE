package com.backend.allreva.diary.query.dto;

import com.backend.allreva.common.model.Image;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
public final class DiaryDetailResponse {

    private final String concertTitle;
    private final Image concertPoster;

    private final LocalDate date;
    private final String episode;
    private final String seatName;

    private final Set<Image> diaryImages;
    private final String content;

    public DiaryDetailResponse(
            final String concertTitle,
            final Image concertPoster,
            final LocalDate date,
            final String episode,
            final String seatName,
            final Set<Image> diaryImages,
            final String content
    ) {
        this.concertTitle = concertTitle;
        this.concertPoster = concertPoster;
        this.date = date;
        this.episode = episode;
        this.seatName = seatName;
        this.diaryImages = diaryImages;
        this.content = content;
    }
}
