package com.backend.allreva.survey.command.domain;

import com.backend.allreva.common.application.BaseEntity;
import com.backend.allreva.survey.command.domain.value.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE survey SET deleted_at = NOW() WHERE id = ?")
@Entity
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "survey_boarding_date",
            joinColumns = @JoinColumn(name = "id", nullable = false)
    )
    private List<LocalDate> boardingDate = new ArrayList<>();

    @Column(nullable = false)
    private String artistName;

    @Column(nullable = false)
    private Region region;

    @Column(nullable = false)
    private LocalDate eddate;

    @Column(nullable = false)
    private int maxPassenger;

    @Column(nullable = true)
    private String information;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private Long memberId;

    private boolean isClosed;


    @Builder
    public Survey(final Long memberId,
                  final Long concertId,
                  final String title,
                  final List<LocalDate> boardingDate,
                  final String artistName,
                  final Region region,
                  final LocalDate eddate,
                  final int maxPassenger,
                  final String information) {
        this.memberId = memberId;
        this.concertId = concertId;
        this.title = title;
        this.boardingDate = boardingDate;
        this.artistName = artistName;
        this.region = region;
        this.eddate = eddate;
        this.maxPassenger = maxPassenger;
        this.information = information;
        this.isClosed = false;
    }

    public void update(final String title,
                       final List<LocalDate> boardingDate,
                       final Region region,
                       final LocalDate eddate,
                       final int maxPassenger,
                       final String information) {
        this.title = title;
        this.boardingDate = boardingDate;
        this.region = region;
        this.eddate = eddate;
        this.maxPassenger = maxPassenger;
        this.information = information;
    }

    public boolean isWriter(final Long loginMemberId) {
        return this.memberId.equals(loginMemberId);
    }
}
