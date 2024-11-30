package com.backend.allreva.diary.infra;

import com.backend.allreva.diary.query.dto.DiaryDetailResponse;
import com.backend.allreva.diary.query.dto.DiarySummaryResponse;

import java.util.List;

public interface DiaryDslRepository {
    DiaryDetailResponse findDetail(Long diaryId, Long memberId);

    List<DiarySummaryResponse> findSummaries(Long memberId, int year, int month);
}
