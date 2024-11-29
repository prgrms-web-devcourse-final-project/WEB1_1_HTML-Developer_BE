package com.backend.allreva.diary.query;

import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.diary.command.application.DiaryCommandService;
import com.backend.allreva.diary.command.application.dto.AddDiaryRequest;
import com.backend.allreva.diary.command.domain.DiaryRepository;
import com.backend.allreva.diary.query.dto.DiaryDetailResponse;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

class DiaryQueryTest extends IntegrationTestSupport {

    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryCommandService diaryCommandService;
    @Autowired
    private DiaryQueryService diaryQueryService;

    private Concert savedConcert;
    private Member savedMember;
    private Long savedDiaryId;

    @BeforeEach
    void setUp() {
        savedConcert = createTestConcert();
        concertRepository.save(savedConcert);
        savedMember = createTestMember();
        memberRepository.save(savedMember);

        AddDiaryRequest request = new AddDiaryRequest(
                savedConcert.getId(),
                LocalDate.now(),
                "episode",
                "content",
                "seatName"
        );
        savedDiaryId = diaryCommandService.add(request, List.of(), savedMember.getId());
    }

    @Transactional
    @DisplayName("공연 기록 상세 조회를 하면 해당 공연 정보와 해당 공연 기록 정보를 출력한다")
    @Test
    void findDetailTest() {

        // When
        DiaryDetailResponse detail = diaryQueryService.findDetailById(savedDiaryId, savedMember.getId());

        // Then
        Assertions.assertThat(detail.getContent()).isEqualTo(diaryRepository.findById(savedDiaryId).get().getContent());
        Assertions.assertThat(detail.getConcertPoster()).isEqualTo(savedConcert.getPoster());
    }

    @Transactional
    @DisplayName("공연 기록 목록 조회를 하면 해당 공연 포스터와 해당 기록 날짜 정보를 출력한다")
    @Test
    void findSummaryTest() {

        // When
        var summaries = diaryQueryService.findSummaries(
                savedMember.getId(),
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue()
        );

        // Then
        Assertions.assertThat(summaries.size()).isEqualTo(1);
        Assertions.assertThat(summaries.get(0).getConcertPoster()).isEqualTo(savedConcert.getPoster());
        Assertions.assertThat(summaries.get(0).getDate()).isEqualTo(LocalDate.now());
    }
}
