package com.backend.allreva.concert.infra;

import com.backend.allreva.common.exception.NotFoundException;
import com.backend.allreva.concert.command.domain.ViewAddedEvent;
import com.backend.allreva.concert.infra.elasticsearch.ConcertDocument;
import com.backend.allreva.concert.infra.elasticsearch.ConcertSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertEventHandler {

    private final ConcertSearchRepository concertSearchRepository;

    @Async
    @EventListener
    public void onMessage(final ViewAddedEvent event) {
        ConcertDocument concertDocument = concertSearchRepository.findByConcertCode(event.getConcertCode())
                .orElseThrow(NotFoundException::new);

        concertDocument.updateViewCount(event.getViewCount());
    }

}
