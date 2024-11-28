package com.backend.allreva.concert.query.application.dto;

import com.backend.allreva.search.query.domain.ConcertDocument;

import java.time.LocalDate;

public record ConcertThumbnail(
        String poster,
        String title,
        String concertHallName,
        LocalDate stdate,
        LocalDate eddate,
        Long id
) {
        public static ConcertThumbnail from(final ConcertDocument concertDocument) {
            return new ConcertThumbnail(
                    concertDocument.getPoster(),
                    concertDocument.getTitle(),
                    concertDocument.getConcertHallName(),
                    concertDocument.getStdate(),
                    concertDocument.getEddate(),
                    Long.parseLong(concertDocument.getId())
            );
        }
}
