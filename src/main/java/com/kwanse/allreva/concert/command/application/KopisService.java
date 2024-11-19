package com.kwanse.allreva.concert.command.application;

import com.kwanse.allreva.concert.command.dto.KopisConcertResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface KopisService {

    Mono<List<String>> fetchConcertCodes(String hallId, boolean isDaily);
    Mono<KopisConcertResponse> fetchConcertDetail(String hallId, String concertcd);
}
