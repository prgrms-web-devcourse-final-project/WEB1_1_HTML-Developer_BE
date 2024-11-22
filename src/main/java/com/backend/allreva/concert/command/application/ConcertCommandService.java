package com.backend.allreva.concert.command.application;

import com.backend.allreva.concert.command.application.dto.AddConcertRequest;

import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertCommandService {

    private final ConcertRepository concertRepository;

    public Long save(AddConcertRequest request) {
        Concert concert = request.to();
        return concertRepository.save(concert).getId();
    }
}
