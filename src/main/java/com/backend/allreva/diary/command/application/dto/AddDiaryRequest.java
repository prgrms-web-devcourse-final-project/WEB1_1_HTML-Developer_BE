package com.backend.allreva.diary.command.application.dto;

import com.backend.allreva.diary.command.domain.ConcertDiary;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AddDiaryRequest(

        @NotNull(message = "공연을 선택해야합니다")
        Long concertId,
        @NotNull(message = "날짜를 선택해야합니다")
        LocalDate date,
        @NotNull(message = "회차를 선택해야합니다")
        String episode,

        String content,
        String seatName
) {

    public ConcertDiary to() {
        return ConcertDiary.builder()
                .concertId(concertId)
                .date(date)
                .episode(episode)
                .content(content)
                .seatName(seatName)
                .build();
    }
}
