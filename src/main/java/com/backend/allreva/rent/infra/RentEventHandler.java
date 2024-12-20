package com.backend.allreva.rent.infra;


import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import com.backend.allreva.common.event.DeadLetterQueue;
import com.backend.allreva.common.event.EntityType;
import com.backend.allreva.common.event.Event;
import com.backend.allreva.common.event.EventEntryRepository;
import com.backend.allreva.rent.command.domain.RentDeletedEvent;
import com.backend.allreva.rent.command.domain.RentSaveEvent;
import com.backend.allreva.rent.infra.elasticsearch.RentDocument;
import com.backend.allreva.rent.infra.elasticsearch.RentDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service
public class RentEventHandler {

    private final RentDocumentRepository rentDocumentRepository;

    private final EventEntryRepository eventEntryRepository;
    private final DeadLetterQueue deadLetterQueue;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onMessage(final RentSaveEvent event) {
        if (isEventExpired(event)) {
            return;
        }
        try {
            RentDocument rentDocument = event.to();
            rentDocumentRepository.save(rentDocument);
            log.info("RentSavedEvent Sync 완료!! rentId: {}", event.getRentId());
        } catch (ElasticsearchException | DataAccessException e) {
            deadLetterQueue.put(event);
            log.info("RentSavedEvent 가 DeadLetterQueue 로 발송 성공!! rentId: {}", event.getRentId());
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onMessage(final RentDeletedEvent event) {
        if (isEventExpired(event)) {
            return;
        }
        try {
            Long rentId = event.getRentId();
            rentDocumentRepository.deleteById(rentId.toString());
            log.info("RentDeletedEvent Sync 완료!! rentId: {}", rentId);
        } catch (ElasticsearchException | DataAccessException e) {
            deadLetterQueue.put(event);
            log.info("RentDeletedEvent 가 DeadLetterQueue 로 발송 성공!! rentId: {}", event.getRentId());
        }
    }

    private boolean isEventExpired(final RentSaveEvent event) {
        Long rentId = event.getRentId();
        return isEventExpired(rentId, event);
    }

    private boolean isEventExpired(final RentDeletedEvent event) {
        Long rentId = event.getRentId();
        return isEventExpired(rentId, event);
    }

    private boolean isEventExpired(final Long rentId, final Event event) {
        int affectedRows = eventEntryRepository.upsert(
                EntityType.RENT.name(),
                rentId.toString(),
                event.getTimestamp()
        );
        return affectedRows == 0;
    }
}
