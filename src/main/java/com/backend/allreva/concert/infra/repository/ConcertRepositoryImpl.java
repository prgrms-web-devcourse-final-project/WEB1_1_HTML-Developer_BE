package com.backend.allreva.concert.infra.repository;

import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.query.application.dto.ConcertDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Repository
public class ConcertRepositoryImpl implements ConcertRepository {

    private final Map<Long, ConcertDetailResponse> cache = new ConcurrentHashMap<>();
    private final Map<Long, Integer> viewCountCache = new ConcurrentHashMap<>();

    private final ConcertJpaRepository concertJpaRepository;


    @Override
    public Concert save(final Concert concert) {
        Concert savedConcert = concertJpaRepository.save(concert);
        ConcertDetailResponse response = concertJpaRepository.findDetailById(concert.getId());
        cache.put(concert.getId(), response);
        return savedConcert;
    }

    @Override
    public ConcertDetailResponse findDetailById(final Long concertId) {
        if (cache.containsKey(concertId)) {
            return cache.get(concertId);
        }

        ConcertDetailResponse detail = concertJpaRepository.findDetailById(concertId);
        cache.put(concertId, detail);
        return detail;
    }

    @Override
    public void increaseViewCount(final Long concertId) {
        viewCountCache.merge(concertId, 1, Integer::sum);
    }

    @Override
    public void deleteAllInBatch() {
        concertJpaRepository.deleteAllInBatch();
        cache.clear();
    }

    @Override
    public boolean existsByConcertCode(final String concertCode) {
        return concertJpaRepository.existsByCodeConcertCode(concertCode);
    }

    @Override
    public Concert findByConcertCode(final String concertCode) {
        return concertJpaRepository.findByCodeConcertCode(concertCode);
    }

    @Override
    public boolean existsById(final Long concertId) {
        return concertJpaRepository.existsById(concertId);
    }


    @Transactional
    @Scheduled(fixedRateString = "${view.count.schedule.rate}")
    public void addViewCounts() {
        viewCountCache.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .forEach(
                        entry -> concertJpaRepository.findById(entry.getKey())
                                .ifPresent(concert -> concert.addViewCount(entry.getValue()))
                );
        viewCountCache.clear();
    }
}
