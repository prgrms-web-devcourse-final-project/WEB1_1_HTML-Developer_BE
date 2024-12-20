package com.backend.allreva.survey_join.infra;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import com.backend.allreva.common.event.DeadLetterQueue;
import com.backend.allreva.common.event.EntityType;
import com.backend.allreva.common.event.Event;
import com.backend.allreva.common.event.EventEntryRepository;
import com.backend.allreva.common.exception.NotFoundException;
import com.backend.allreva.survey.infra.elasticsearch.SurveyDocument;
import com.backend.allreva.survey.infra.elasticsearch.SurveyDocumentRepository;
import com.backend.allreva.survey_join.command.domain.SurveyJoinEvent;
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
public class SurveyJoinEventHandler {

    private final SurveyDocumentRepository surveyDocumentRepository;

    private final EventEntryRepository eventEntryRepository;
    private final DeadLetterQueue deadLetterQueue;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onMessage(final SurveyJoinEvent event) {
        if (isEventExpired(event)) {
            return;
        }
        try {
            Long surveyId = event.getSurveyId();
            SurveyDocument surveyDocument = surveyDocumentRepository.findById(surveyId.toString())
                    .orElseThrow(NotFoundException::new);

            surveyDocument.updateParticipationCount(event.getParticipationCount());
            surveyDocumentRepository.save(surveyDocument);
            log.info("SurveyJoinEvent Sync 완료!! surveyId: {}", event.getSurveyId());

        } catch (ElasticsearchException | DataAccessException e) {
            deadLetterQueue.put(event);
            log.info("SurveyJoinEvent 가 DeadLetterQueue 로 발송 성공!! surveyId: {}", event.getSurveyId());
        }
    }


    private boolean isEventExpired(final SurveyJoinEvent event) {
        Long surveyId = event.getSurveyId();
        return isEventExpired(surveyId, event);
    }
    private boolean isEventExpired(final Long surveyId, final Event event) {
        int affectedRows = eventEntryRepository.upsert(
                EntityType.SURVEY.name(),
                surveyId.toString(),
                event.getTimestamp()
        );

        return affectedRows == 0;
    }

}
