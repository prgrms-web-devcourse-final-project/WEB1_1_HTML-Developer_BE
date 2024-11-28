package com.backend.allreva.concert.query.application.dto;

import java.util.List;

public record ConcertMainResponse(
        List<ConcertThumbnail> concertThumbnails,
        List<Object> searchAfter
) {
        public static ConcertMainResponse from(final List<ConcertThumbnail> concertThumbnails, final List<Object> searchAfter) {
            return new ConcertMainResponse(concertThumbnails, searchAfter);
        }
}
