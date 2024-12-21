package com.backend.allreva.common.event;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.backend.allreva.common.event.QEventEntry.eventEntry;

@RequiredArgsConstructor
@Repository
public class EventEntryDslRepositoryImpl implements EventEntryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isValidEvent(
            final EntityType entityType,
            final String entityId,
            final Long timestamp
    ) {
        Integer result = queryFactory
                .selectOne()
                .from(eventEntry)
                .where(
                        eventEntry.entityId.eq(entityId),
                        eventEntry.entityType.eq(entityType),
                        eventEntry.timestamp.loe(timestamp)
                )
                .fetchFirst();

        return result != null;
    }
}
