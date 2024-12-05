package com.backend.allreva.search.infra;

import com.backend.allreva.search.query.application.PopularKeywordRepository;
import com.backend.allreva.search.query.application.dto.ChangeStatus;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class PopularKeywordRepositoryImpl implements PopularKeywordRepository {
    private static final String KEYWORD_KEY = "keyword";
    private static final String ONE_HOUR_KEYWORD_KEY = "one-hour-keyword";
    private static final String HASH_KEYWORD_KEY = "keyword-change-status";

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, String> zSetOperations;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOperations;

    /**
     * 인기 검색어 top 10 조회
     */
    @Override
    public List<String> getPopularKeyword() {
        Set<String> result = zSetOperations.reverseRange(KEYWORD_KEY, 0, 10);
        if (result == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(result);
    }

    /**
     * keyword의 status 조회
     */
    @Override
    public String getKeywordChangeStatus(String keyword) {
        return hashOperations.get(HASH_KEYWORD_KEY, keyword);
    }

    /**
     * keyword의 status set
     */
    @Override

    public void setKeywordChangeStatus(String keyword, ChangeStatus status) {
        hashOperations.put(HASH_KEYWORD_KEY, keyword, status.name());
    }


    /**
     * 키워드 count update
     */
    @Override
    public void updateKeywordCount(String keyword, Double count) {
        zSetOperations.incrementScore(ONE_HOUR_KEYWORD_KEY, keyword, count);
    }

    @Override
    public void decreaseAllKeywordCount() {
        Set<ZSetOperations.TypedTuple<String>> keywords = zSetOperations.rangeWithScores(KEYWORD_KEY, 0, -1);

        keywords.forEach(keyword -> {
            if (keyword.getValue() == null || keyword.getScore() == null) return;

            // 50% 가중치 낮추기
            double newScore = keyword.getScore() * 0.5;
            zSetOperations.add(KEYWORD_KEY, keyword.getValue(), newScore);
        });
    }


    @Override
    public void deleteOneHourKeyword() {
        zSetOperations.removeRange(ONE_HOUR_KEYWORD_KEY, 0, -1); // 1시간 검색어 삭제
    }

    /**
     * 전체 one hour keyword zset 조회
     */
    @Override

    public Set<String> getAllOneHourKeywords() {
        return zSetOperations.range(ONE_HOUR_KEYWORD_KEY, 0, -1);
    }

    /**
     * 해당 키워드의 one hour count 수 조회
     */
    @Override

    public Double getOneHourKeywordScore(String keyword) {
        return zSetOperations.score(ONE_HOUR_KEYWORD_KEY, keyword);
    }


}
