package com.backend.allreva.concert.query.application.dto;

import java.util.List;

public record ConcertMainResponse(
        List<ConcertMain> concertMain,
        List<Object> searchAfter
) {
        public static ConcertMainResponse from(final List<ConcertMain> concertMain, final List<Object> searchAfter) {
            return new ConcertMainResponse(concertMain, searchAfter);
        }
}
