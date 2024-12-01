package com.backend.allreva.survey.command.domain;

import com.backend.allreva.common.model.BaseEntity;
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
@SQLDelete(sql = "UPDATE survey_boarding_date SET deleted_at = NOW() WHERE id = ?")
public class SurveyBoardingDate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Survey survey;

    @Column(nullable = false)
    private LocalDate date;

    @Builder
    public SurveyBoardingDate(Survey survey, LocalDate date) {
        this.survey = survey;
        this.date = date;
    }
}
