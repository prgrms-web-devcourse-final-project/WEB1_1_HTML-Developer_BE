package com.backend.allreva.common.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntry, Long> {

    @Query("SELECT e " +
            "FROM EventEntry e " +
            "WHERE e.isPublished = false " +
            "ORDER BY e.createdAt ASC ")
    List<EventEntry> findAllRemainEvents();

    @Modifying
    @Transactional
    @Query("UPDATE EventEntry e SET e.isPublished = true " +
            "WHERE e.id IN :ids AND e.isPublished = false ")
    void updateAllRemainEvents(@Param("ids") List<Long> ids);
}
