package com.backend.allreva.diary.command.domain;

import com.backend.allreva.diary.infra.DiaryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<ConcertDiary, Long>, DiaryDslRepository {
}
