package com.backend.allreva.rent.query.application.dto;

import com.backend.allreva.rent.query.application.domain.RentDocument;

import java.time.LocalDate;

public record RentThumbnail(
        String title,
        String boardingArea,
        String imageUrl,
        LocalDate edDate
) {
        public static RentThumbnail from (RentDocument document) {
            return new RentThumbnail(
                    document.getTitle(),
                    document.getBoardingArea(),
                    document.getImageUrl(),
                    document.getEdDate()
            );
        }
}
