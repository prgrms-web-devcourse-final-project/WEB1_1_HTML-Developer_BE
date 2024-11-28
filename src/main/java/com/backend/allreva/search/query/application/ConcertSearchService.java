package com.backend.allreva.search.query.application;

import com.backend.allreva.concert.query.application.dto.ConcertThumbnail;
import com.backend.allreva.search.infra.ConcertSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertSearchService {
    private final ConcertSearchRepository concertSearchRepository;

    public List<ConcertThumbnail> searchConcertThumbnails(String title) {}
}
