package com.backend.allreva.diary.command.domain;

import com.backend.allreva.common.model.BaseEntity;
import com.backend.allreva.common.model.Image;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE concert_diary SET deleted_at = NOW() WHERE id = ?")
@Entity
public class ConcertDiary extends BaseEntity {
    @Id
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private Date date; //날짜

    private String episode; //회차

    private String content;

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "diary_images",
            joinColumns = @JoinColumn(name = "id")
    )
    private List<Image> diaryImages = new ArrayList<>();
}
