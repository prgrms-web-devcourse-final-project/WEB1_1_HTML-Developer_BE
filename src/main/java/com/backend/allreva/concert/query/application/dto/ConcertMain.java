package com.backend.allreva.concert.query.application.dto;

import com.backend.allreva.search.query.domain.ConcertDocument;

import java.time.LocalDate;

public record ConcertMain(
        String poster,
        String title,
        String concertHallName,
        LocalDate stdate,
        LocalDate eddate
) {
        public static ConcertMain from(final ConcertDocument concertDocument) {
            return new ConcertMain(
                    concertDocument.getPoster(),
                    concertDocument.getTitle(),
                    concertDocument.getConcertHallName(),
                    concertDocument.getStdate(),
                    concertDocument.getEddate()
            );
        }
}
