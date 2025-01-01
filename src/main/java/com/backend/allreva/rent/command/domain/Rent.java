package com.backend.allreva.rent.command.domain;

import com.backend.allreva.common.event.Events;
import com.backend.allreva.common.model.BaseEntity;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.rent.command.application.request.RentUpdateRequest;
import com.backend.allreva.rent.command.domain.value.*;
import com.backend.allreva.rent.exception.RentAccessDeniedException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE rent_form SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "rent_form")
public class Rent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long concertId;

    @Embedded
    private DetailInfo detailInfo;

    @Embedded
    private OperationInfo operationInfo;

    @Embedded
    private AdditionalInfo additionalInfo;

    @Builder.Default
    @OneToMany(mappedBy = "rent", cascade = CascadeType.ALL)
    private List<RentBoardingDate> boardingDates = new ArrayList<>();

    @Builder.Default
    @Column(nullable = false)
    private boolean isClosed = false; //마감 여부

    public void assignBoardingDates(List<RentBoardingDate> boardingDates) {
        boardingDates.forEach(boardingDate -> boardingDate.assignRent(this));
        this.boardingDates = boardingDates;
    }

    public void updateRent(
            final RentUpdateRequest request,
            final Image image
    ) {
        this.detailInfo = DetailInfo.builder()
                .title(detailInfo.getTitle())
                .artistName(detailInfo.getArtistName())
                .image(image)
                .region(request.region())
                .depositAccount(detailInfo.getDepositAccount())
                .build();
        this.operationInfo = OperationInfo.builder()
                .boardingArea(request.boardingArea())
                .upTime(request.upTime())
                .downTime(request.downTime())
                .bus(Bus.builder()
                        .busSize(request.busSize())
                        .busType(request.busType())
                        .maxPassenger(request.maxPassenger())
                        .build())
                .price(Price.builder()
                        .roundPrice(request.roundPrice())
                        .upTimePrice(request.upTimePrice())
                        .downTimePrice(request.downTimePrice())
                        .build())
                .build();
        this.additionalInfo = AdditionalInfo.builder()
                .recruitmentCount(request.recruitmentCount())
                .chatUrl(request.chatUrl())
                .refundType(request.refundType())
                .information(request.information())
                .endDate(request.endDate())
                .build();
        List<RentBoardingDate> rentBoardingDates = request.rentBoardingDateRequests().stream()
                .map(date -> RentBoardingDate.builder()
                        .rent(this)
                        .date(date)
                        .build())
                .toList();
        assignBoardingDates(rentBoardingDates);
        isClosed = false;

        Events.raise(new RentSaveEvent(this));
    }

    public void validateMine(Long memberId) {
        if (!this.memberId.equals(memberId)) {
            throw new RentAccessDeniedException();
        }
    }

    public void close() {
        isClosed = true;
        Events.raise(new RentDeletedEvent(id));
    }
}
