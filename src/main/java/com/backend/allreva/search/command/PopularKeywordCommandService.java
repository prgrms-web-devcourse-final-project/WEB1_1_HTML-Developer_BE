package com.backend.allreva.search.command;

import com.backend.allreva.search.infra.PopularKeywordRepository;
import com.backend.allreva.search.query.application.dto.ChangeStatus;
import com.backend.allreva.search.query.application.dto.PopularKeywordResponse;
import com.backend.allreva.search.query.application.dto.PopularKeywordResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopularKeywordCommandService {
    private final PopularKeywordRepository popularKeywordRepository;

    /**
     * 검색어 count update
     */
    public void updateKeywordCount(final String keyword) {
        popularKeywordRepository.updateKeywordCount(keyword, 1.0);
    }

    /**
     * 가중치 낮춤
     */
    public void decreaseAllKeywordCount() {
        popularKeywordRepository.decreaseAllKeywordCount();
    }

    /**
     * 인기검색어 스케줄러를 통해 1시간마다 순위 업데이트
     */
    public void updatePopularKeywordRank() {
        //1. 인기 검색어 목록 가져오기
        List<String> top10 = popularKeywordRepository.getPopularKeywordRank().popularKeywordResponses()
                .stream().map(PopularKeywordResponse::keyword).collect(Collectors.toList()); // 기존 top10

        //2. 현재 랭킹가져오기
        List<String> updatedTop10 = popularKeywordRepository.getTop10Keywords();

        //3. 랭킹 업데이트
        compareAndSaveRankChanges(top10, updatedTop10);
    }

    public void compareAndSaveRankChanges(final List<String> top10, final List<String> updatedTop10) {
        List<PopularKeywordResponse> list = new ArrayList<>();
        for (int i = 0; i < updatedTop10.size(); i++) {
            String keyword = updatedTop10.get(i);
            int oldRank = top10.indexOf(keyword);

            // 순위 비교
            ChangeStatus status;
            if (oldRank == -1) {
                status = ChangeStatus.UP;
            } else if (i < oldRank) {
                status = ChangeStatus.UP;
            } else if (i > oldRank) {
                status = ChangeStatus.DOWN;
            } else {
                status = ChangeStatus.STAY;
            }

            list.add(PopularKeywordResponse
                    .builder()
                    .rank(i + 1)
                    .keyword(keyword)
                    .changeStatus(status)
                    .build());
        }

        popularKeywordRepository.updatePopularKeywordRank(new PopularKeywordResponses(list));
    }
}
