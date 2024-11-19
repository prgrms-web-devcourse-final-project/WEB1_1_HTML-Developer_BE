package com.kwanse.allreva.concert.command.application;

import com.kwanse.allreva.concert.command.application.util.CsvUtil;
import com.kwanse.allreva.concert.command.domain.ConcertRepository;
import com.kwanse.allreva.concert.command.dto.KopisConcertResponse;
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
    private final KopisService kopisService;
    private final ConcertRepository concertRepository;

    //공연정보 받아오기
    public void fetchConcertInfoList() {
        List<String> hallIds = CsvUtil.readConcertHallIds();

        hallIds.forEach(hallId -> {
            kopisService.fetchConcertCodes(hallId, false)
                    .flatMap(concertIds -> {
                        List<Mono<KopisConcertResponse>> concertDetails = new ArrayList<>();
                        concertIds.forEach(concertId -> {
                            Mono<KopisConcertResponse> concertDetailMono = kopisService.fetchConcertDetail(concertId)
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
}


