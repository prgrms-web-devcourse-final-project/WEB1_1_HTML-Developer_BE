package com.backend.allreva.survey.command.domain;

import com.backend.allreva.common.application.BaseEntity;
import com.backend.allreva.survey.command.domain.value.BoardingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE survey_join SET deleted_at = NOW() WHERE id = ?")
public class SurveyJoin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate boardingDate;

    @Column(nullable = false)
    private BoardingType boardingType;

    @Column(nullable = false)
    private int passengerNum;

    @Column(nullable = false)
    private boolean notified;

    @Column(nullable = false)
    private Long surveyId;

    @Column(nullable = false)
    private Long memberId;

    @Builder
    public SurveyJoin(final Long memberId,
                      final Long surveyId,
                      final LocalDate boardingDate,
                      final BoardingType boardingType,
                      final int passengerNum,
                      final boolean notified) {
        this.memberId = memberId;
        this.surveyId = surveyId;
        this.boardingDate = boardingDate;
        this.boardingType = boardingType;
        this.passengerNum = passengerNum;
        this.notified = notified;

    }
}
