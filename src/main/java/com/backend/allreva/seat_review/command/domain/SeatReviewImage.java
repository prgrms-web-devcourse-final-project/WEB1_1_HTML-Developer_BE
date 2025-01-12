package com.backend.allreva.seat_review.command.domain;

import com.backend.allreva.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE seat_review SET deleted_at = NOW() WHERE id = ?")
public class SeatReviewImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long seatReviewId;
}
