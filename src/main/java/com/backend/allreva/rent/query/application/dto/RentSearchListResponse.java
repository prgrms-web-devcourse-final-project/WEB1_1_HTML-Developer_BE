package com.backend.allreva.rent.query.application.dto;

import java.util.List;

public record RentSearchListResponse(
        List<RentThumbnail> rentThumbnails,
        List<Object> searchAfter
) {
        public static RentSearchListResponse from(
                final List<RentThumbnail> rentThumbnails,
                final List<Object> searchAfter
        ){
            return new RentSearchListResponse(rentThumbnails, searchAfter);
        }
}
