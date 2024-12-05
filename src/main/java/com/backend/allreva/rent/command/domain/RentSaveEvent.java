package com.backend.allreva.rent.command.domain;

import com.backend.allreva.common.event.Event;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.rent.query.application.domain.RentDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.backend.allreva.common.config.KafkaConfig.TOPIC_RENT_SAVE;

@Getter
@NoArgsConstructor
public class RentSaveEvent extends Event {

    private final String topic = TOPIC_RENT_SAVE;

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

    /*@Builder
    private RentSaveEvent(
            @JsonProperty("rentId") final Long rentId,
            @JsonProperty("title") final String title,
            @JsonProperty("boardingArea") final String boardingArea,
            @JsonProperty("imageUrl") final Image image,
            @JsonProperty("endDate") final LocalDate endDate
    ) {
        this.rentId = rentId;
        this.title = title;
        this.boardingArea = boardingArea;
        this.image = image;
        this.endDate = endDate;
    }*/

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
