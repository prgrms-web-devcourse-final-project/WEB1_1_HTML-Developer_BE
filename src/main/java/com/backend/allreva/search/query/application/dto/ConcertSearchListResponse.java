package com.backend.allreva.search.query.application.dto;

import com.backend.allreva.concert.query.application.dto.ConcertThumbnail;

import java.util.List;

public record ConcertSearchListResponse(
        List<ConcertThumbnail> concertThumbnails,
        List<Object> searchAfter
) {
        public static ConcertSearchListResponse from(
                final List<ConcertThumbnail> concertThumbnails,
                final List<Object> searchAfter) {
            return new ConcertSearchListResponse(concertThumbnails, searchAfter);
        }
}
