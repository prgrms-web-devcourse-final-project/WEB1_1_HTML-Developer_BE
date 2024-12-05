package com.backend.allreva.rent.query.application.dto;

import com.backend.allreva.rent.query.application.domain.RentDocument;

import java.time.LocalDate;

public record RentThumbnail(
        Long id,
        String title,
        String boardingArea,
        String imageUrl,
        LocalDate edDate
) {
        public static RentThumbnail from (RentDocument document) {
            return new RentThumbnail(
                    Long.parseLong(document.getId()),
                    document.getTitle(),
                    document.getBoardingArea(),
                    document.getImageUrl(),
                    document.getEdDate()
            );
        }
}
