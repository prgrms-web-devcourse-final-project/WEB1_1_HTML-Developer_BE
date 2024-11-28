package com.backend.allreva.rent.command.domain;

import com.backend.allreva.common.model.BaseEntity;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.rent.command.application.dto.RentFormUpdateRequest;
import com.backend.allreva.rent.command.application.dto.RentFormUpdateRequest.RentBoardingDateUpdateRequest;
import com.backend.allreva.rent.command.domain.value.AdditionalInfo;
import com.backend.allreva.rent.command.domain.value.Bus;
import com.backend.allreva.rent.command.domain.value.DetailInfo;
import com.backend.allreva.rent.command.domain.value.OperationInfo;
import com.backend.allreva.rent.command.domain.value.Price;
import com.backend.allreva.rent.exception.RentFormAccessDeniedException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE rent_form SET deleted_at = NOW() WHERE id = ?")
@Entity
public class RentForm extends BaseEntity {
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

    @Column(nullable = false)
    private boolean isClosed; //마감 여부

    public void updateRentForm(RentFormUpdateRequest request) {
        this.detailInfo = DetailInfo.builder()
                .image(new Image(request.imageUrl()))
                .region(request.region())
                .build();
        this.operationInfo = OperationInfo.builder()
                .boardingArea(request.boardingArea())
                .boardingDates(request.rentBoardingDateRequests().stream()
                        .map(RentBoardingDateUpdateRequest::date)
                        .toList())
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
        isClosed = false;
    }

    public void validateMine(Long memberId) {
        if (!this.memberId.equals(memberId)) {
            throw new RentFormAccessDeniedException();
        }
    }

    public void close() {
        isClosed = true;
    }
}
