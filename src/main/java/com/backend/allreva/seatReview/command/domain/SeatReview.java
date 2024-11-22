package com.backend.allreva.seatReview.command.domain;

import com.backend.allreva.common.model.Image;
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

import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(
            name = "seat_review_image", // 매핑될 테이블 이름
            joinColumns = @JoinColumn(name = "review_id") // 외래 키 설정
    )
    private List<Image> images = new ArrayList<>();

    private Long member_id;

    private Long hall_id;
}
