package com.backend.allreva.diary.query;

import com.backend.allreva.diary.command.domain.DiaryRepository;
import com.backend.allreva.diary.query.response.DiaryDetailResponse;
import com.backend.allreva.diary.query.response.DiarySummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryQueryService {

    private final DiaryRepository diaryRepository;

    public DiaryDetailResponse findDetailById(
            final Long diaryId,
            final Long memberId
    ) {
        return diaryRepository.findDetail(diaryId, memberId);
    }

    public List<DiarySummaryResponse> findSummaries(
            final Long memberId,
            final int year,
            final int month
    ) {
        return diaryRepository.findSummaries(memberId, year, month);
    }
}
