package com.backend.allreva.common.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntry, Long> {

}
