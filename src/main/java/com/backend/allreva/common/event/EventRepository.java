package com.backend.allreva.common.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntry, Long> {

    @Query("SELECT e " +
            "FROM EventEntry e " +
            "ORDER BY e.createdAt ASC ")
    List<EventEntry> findAllRemainEvents();

}
