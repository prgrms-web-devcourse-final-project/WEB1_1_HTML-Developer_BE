package com.backend.allreva.diary.command;

import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.diary.command.application.DiaryCommandService;
import com.backend.allreva.diary.command.application.dto.AddDiaryRequest;
import com.backend.allreva.diary.command.domain.ConcertDiary;
import com.backend.allreva.diary.command.domain.DiaryRepository;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class DiaryCommandTest extends IntegrationTestSupport {

    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryCommandService diaryCommandService;

    @DisplayName("공연 기록에 대한 정보를 입력하면 데이터베이스에 저장된다")
    @Test
    void saveDiaryTest() {

        // Given
        Concert concert = createTestConcert();
        concertRepository.save(concert);
        Member member = createTestMember();
        memberRepository.save(member);

        AddDiaryRequest request = new AddDiaryRequest(
                concert.getId(),
                LocalDate.now(),
                "episode",
                "content",
                "seatName"
        );


        // When
        Long diaryId = diaryCommandService.add(request, List.of(), member.getId());
        ConcertDiary diary = diaryRepository.findById(diaryId).get();

        // Then
        Assertions.assertThat(diary.getConcertId()).isEqualTo(concert.getId());
    }

}
