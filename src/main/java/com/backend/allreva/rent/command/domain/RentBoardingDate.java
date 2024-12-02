package com.backend.allreva.rent.command.domain;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE rent_form_boarding_date SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "rent_form_boarding_date")
public class RentBoardingDate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rent rent;

    @Column(nullable = false)
    private LocalDate date;

    @Builder
    private RentBoardingDate(Rent rent, LocalDate date) {
        this.rent = rent;
        this.date = date;
    }
}

