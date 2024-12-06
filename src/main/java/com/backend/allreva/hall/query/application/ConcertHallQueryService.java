package com.backend.allreva.hall.query.application;

import com.backend.allreva.hall.command.domain.ConcertHallRepository;
import com.backend.allreva.hall.query.application.response.ConcertHallDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertHallQueryService {

    private final ConcertHallRepository concertHallRepository;

    public ConcertHallDetailResponse findDetailByHallCode(final String hallCode) {
        return concertHallRepository.findDetailByHallCode(hallCode);
    }

}
