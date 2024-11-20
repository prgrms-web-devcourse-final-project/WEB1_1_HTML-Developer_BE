package com.backend.allreva.hall.command.application;

import com.backend.allreva.common.util.CsvUtil;
import com.backend.allreva.hall.command.application.dto.KopisHallResponse;
import com.backend.allreva.hall.command.domain.ConcertHallRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminHallService {
    private final KopisHallService kopisHallService;
    private final ConcertHallRepository concertHallRepository;

    //공연장 정보 받아오기
    public void fetchConcertHallInfoList() {
        List<String> hallIds = CsvUtil.readConcertHallIds();
        hallIds.parallelStream()
                .forEach(hallId -> {
                    KopisHallResponse response = kopisHallService.fetchConcertHallDetail(hallId).block();
                    if (response != null)
                        concertHallRepository.save(KopisHallResponse.toEntity(response));
                    log.info("hall detail fetch complete for hallId: {}", hallId);
                });
        log.info("All hall detail fetch complete");
    }
}
