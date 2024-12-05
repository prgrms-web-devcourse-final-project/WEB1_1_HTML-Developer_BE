package com.backend.allreva.search.query.application;

import com.backend.allreva.search.query.application.dto.ChangeStatus;
import com.backend.allreva.search.query.application.dto.PopularKeywordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PopularKeywordService {
    private final PopularKeywordRepository popularKeywordRepository;

    /**
     * 인기 검색어 Top 10 조회
     */
    public List<PopularKeywordResponse> getPopularKeywordRank() {
        List<String> top10 = popularKeywordRepository.getPopularKeyword();
        List<PopularKeywordResponse> responseList = new ArrayList<>();

        for (int i = 1; i <= top10.size(); i++) {
            String keyword = top10.get(i);
            String changeStatus = popularKeywordRepository.getKeywordChangeStatus(keyword);

            responseList.add(
                    PopularKeywordResponse.builder()
                            .rank(i)
                            .keyword(keyword)
                            .changeStatus(changeStatus)
                            .build()
            );
        }
        return responseList;
    }

    /**
     * 검색어 count update
     */
    public void updateKeywordCount(String keyword) {
        popularKeywordRepository.updateKeywordCount(keyword, 1.0);
    }

    /**
     * 인기검색어 스케줄러를 통해 1시간마다 순위 업데이트
     */
    public void updatePopularKeywordRank() {
        // 1. 인기 검색어 목록 가져오기
        List<String> top10 = popularKeywordRepository.getPopularKeyword(); // 기존 top10

        //2. 가중치 낮추기
        popularKeywordRepository.decreaseAllKeywordCount();

        //3. 1시간 검색어 count를 합치기
        Set<String> oneHourKeywords = popularKeywordRepository.getAllOneHourKeywords(); // 1시간 검색어
        for (String keyword : oneHourKeywords) {
            Double score = popularKeywordRepository.getOneHourKeywordScore(keyword);
            popularKeywordRepository.updateKeywordCount(keyword, score);
        }

        // 4. 새롭게 계산된 top10 얻기
        List<String> updatedTop10 = popularKeywordRepository.getPopularKeyword();

        //5. ChangeStatus 저장
        compareAndSaveRankChanges(top10, updatedTop10);

        // 6. 1시간 검색어 삭제
        popularKeywordRepository.deleteOneHourKeyword();
    }

    public void compareAndSaveRankChanges(List<String> currentTop10, List<String> updatedTop10) {
        Map<String, ChangeStatus> keywordRankChanges = new HashMap<>();

        for (int i = 0; i < updatedTop10.size(); i++) {
            String keyword = updatedTop10.get(i);
            int oldRank = currentTop10.indexOf(keyword);

            // 순위 비교
            ChangeStatus status = (i < oldRank) ? ChangeStatus.UP
                    : (i > oldRank) ? ChangeStatus.DOWN
                    : ChangeStatus.STAY;
            keywordRankChanges.put(keyword, status);
        }

        keywordRankChanges.forEach(popularKeywordRepository::setKeywordChangeStatus);
    }
}
