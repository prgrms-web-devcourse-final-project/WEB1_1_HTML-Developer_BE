package com.backend.allreva.concert.command.application;

import com.backend.allreva.common.util.CsvUtil;
import com.backend.allreva.concert.command.application.dto.KopisConcertResponse;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminConcertService {
    private final KopisConcertService kopisConcertService;
    private final ConcertRepository concertRepository;

    //공연정보 받아오기
    public void fetchConcertInfoList() {
        List<String> hallIds = CsvUtil.readConcertHallIds();

        hallIds.forEach(hallId -> {
            kopisConcertService.fetchConcertCodes(hallId, false)
                    .flatMap(concertCodes -> {
                        List<Mono<KopisConcertResponse>> concertDetails = new ArrayList<>();
                        concertCodes.forEach(concertCode -> {
                            Mono<KopisConcertResponse> concertDetailMono = kopisConcertService.fetchConcertDetail(hallId, concertCode)
                                    .publishOn(Schedulers.boundedElastic()) //DB에 저장할 때 블로킹되므로 스레드 따로 할당
                                    .doOnNext(concert -> {
                                        if (concert != null)
                                            concertRepository.save(KopisConcertResponse.toEntity(concert)); // 비동기적으로 저장
                                    });
                            concertDetails.add(concertDetailMono);
                        });

                        return Mono.when(concertDetails)
                                .doOnTerminate(() -> log.info("All concert details processed for hallId: {}", hallId));
                    })
                    .subscribe(); // 비동기적으로 처리 시작
        });
    }

    //매일 업데이트 함수
    public void fetchDailyConcertInfoList() {
        List<String> hallIds = CsvUtil.readConcertHallIds();
        hallIds.forEach(hallId -> {
            kopisConcertService.fetchConcertCodes(hallId, true)
                    .flatMap(concertCodes -> {
                        List<Mono<KopisConcertResponse>> concertDetails = new ArrayList<>();
                        concertCodes.forEach(concertCode -> {
                            Mono<KopisConcertResponse> concertDetailMono = kopisConcertService.fetchConcertDetail(hallId, concertCode)
                                    .publishOn(Schedulers.boundedElastic()) //DB에 저장할 때 블로킹되므로 스레드 따로 할당
                                    .doOnNext(concert -> {
                                        if (concert != null) {
                                            // 해당 concertCode가 있는지 확인
                                            boolean isExist = concertRepository.existsByconcertCode(concert.getConcertcd());

                                            if (isExist) {
                                                // 해당 concertCode가 있으면 정보 업데이트
                                                Concert existingConcert = concertRepository.findByconcertCode(concert.getConcertcd());
                                                existingConcert.updateFrom(concert); // 엔티티에서 정보를 업데이트하는 메소드
                                                concertRepository.save(existingConcert); // 업데이트된 정보를 저장
                                            } else {
                                                // 해당 concertCode가 없으면 새로 추가
                                                concertRepository.save(KopisConcertResponse.toEntity(concert)); // 새로운 정보를 저장
                                            }
                                        }
                                    });

                            concertDetails.add(concertDetailMono);
                        });

                        return Mono.when(concertDetails)
                                .doOnTerminate(() -> log.info("All concert details processed for hallId: {}", hallId));
                    })
                    .subscribe(); // 비동기적으로 처리 시작
        });
    }
}


