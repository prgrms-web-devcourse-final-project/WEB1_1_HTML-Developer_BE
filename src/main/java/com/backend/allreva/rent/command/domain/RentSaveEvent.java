package com.backend.allreva.rent.command.domain;

import com.backend.allreva.common.event.Event;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.rent.infra.elasticsearch.RentDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RentSaveEvent extends Event {

    public static final String TOPIC_RENT_SAVE = "rent-save";

    private Long rentId;
    private String title;
    private String boardingArea;
    private Image image;
    private LocalDate endDate;

    public RentSaveEvent(final Rent rent) {
        this.rentId = rent.getId();
        this.title = rent.getDetailInfo().getTitle();
        this.boardingArea = rent.getOperationInfo().getBoardingArea();
        this.image = rent.getDetailInfo().getImage();
        this.endDate = rent.getAdditionalInfo().getEndDate();
    }

    public RentDocument to() {
        return RentDocument.builder()
                .id(rentId.toString())
                .title(title)
                .boardingArea(boardingArea)
                .imageUrl(image.getUrl())
                .edDate(endDate)
                .build();
    }
}
