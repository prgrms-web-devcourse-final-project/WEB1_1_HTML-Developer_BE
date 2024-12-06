package com.backend.allreva.diary.infra;

import com.backend.allreva.diary.query.response.DiaryDetailResponse;
import com.backend.allreva.diary.query.response.DiarySummaryResponse;

import java.util.List;

public interface DiaryDslRepository {
    DiaryDetailResponse findDetail(Long diaryId, Long memberId);

    List<DiarySummaryResponse> findSummaries(Long memberId, int year, int month);
}
