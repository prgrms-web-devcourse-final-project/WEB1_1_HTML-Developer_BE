package com.backend.allreva.concert.command.application;

import com.backend.allreva.hall.command.application.dto.KopisHallResponse;
import com.backend.allreva.concert.command.application.dto.KopisConcertResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface KopisConcertService {

    Mono<List<String>> fetchConcertCodes(String hallId, boolean isDaily);
    Mono<KopisConcertResponse> fetchConcertDetail(String hallId, String concertcd);
}
