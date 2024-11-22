package com.backend.allreva.seatReview.command.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Range;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE seat_review SET deleted_at = NOW() WHERE id = ?")
public class SeatReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seat;

    @Column(nullable = false)
    private String content;

    @Range(min = 0, max = 5)
    private int star;

    private Long member_id;

    private Long hall_id;
}
