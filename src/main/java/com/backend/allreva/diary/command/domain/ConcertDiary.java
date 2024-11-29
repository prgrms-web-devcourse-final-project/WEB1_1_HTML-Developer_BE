package com.backend.allreva.diary.command.domain;

import com.backend.allreva.common.model.BaseEntity;
import com.backend.allreva.common.model.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE concert_diary SET deleted_at = NOW() WHERE id = ?")
@Table(indexes = {
        @Index(name = "idx_diary_member_date", columnList = "member_id, date")
})
@Entity
public class ConcertDiary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long concertId;
    private Image concertPoster;

    @Column(nullable = false)
    private LocalDate date; //날짜

    @Column(nullable = false)
    private String episode; //회차

    private String content;
    private String seatName;


    @ElementCollection
    @CollectionTable(
            name = "diary_image",
            joinColumns = @JoinColumn(name = "id")
    )
    private Set<Image> diaryImages = new HashSet<>();

    @Builder
    public ConcertDiary(
            Long memberId,
            Long concertId,
            LocalDate date,
            String episode,
            String content,
            String seatName
    ) {
        this.memberId = memberId;
        this.concertId = concertId;
        this.date = date;
        this.episode = episode;
        this.content = content;
        this.seatName = seatName;
    }

    public void addImages(List<Image> images) {
        this.diaryImages.addAll(images);
    }

    public void addMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public boolean isWriter(Long memberId) {
        return this.memberId.equals(memberId);
    }

    public void update(
            Long memberId,
            Long concertId,
            LocalDate date,
            String episode,
            String content,
            String seatName,
            List<Image> diaryImages
    ) {
        this.memberId = memberId;
        this.concertId = concertId;
        this.date = date;
        this.episode = episode;
        this.content = content;
        this.seatName = seatName;
        addImages(diaryImages);
    }
}
