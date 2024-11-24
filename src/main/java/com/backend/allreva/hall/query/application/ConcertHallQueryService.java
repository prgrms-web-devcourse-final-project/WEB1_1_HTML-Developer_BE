package com.backend.allreva.hall.query.application;

import com.backend.allreva.hall.command.domain.ConcertHallRepository;
import com.backend.allreva.hall.query.application.dto.ConcertHallDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ConcertHallQueryService {

    private final ConcertHallRepository concertHallRepository;

    public ConcertHallDetail findDetailByHallCode(String hallCode) {
        return concertHallRepository.findDetailByHallCode(hallCode);
    }

}
