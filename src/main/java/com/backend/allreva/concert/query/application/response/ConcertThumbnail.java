package com.backend.allreva.concert.query.application.response;

import com.backend.allreva.concert.infra.elasticsearch.ConcertDocument;

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
                    concertDocument.getStDate(),
                    concertDocument.getEdDate(),
                    Long.parseLong(concertDocument.getId())
            );
        }
}
