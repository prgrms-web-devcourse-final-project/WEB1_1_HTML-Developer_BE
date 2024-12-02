package com.backend.allreva.diary.infra;

import com.backend.allreva.common.application.S3ImageRepository;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.diary.command.domain.DiaryCreatedEvent;
import com.backend.allreva.diary.command.domain.DiaryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryListener {

    private static final String TOPIC = "diaryImage";
    private static final String GROUP_ID = "diary";

    private final DiaryRepository diaryRepository;
    private final S3ImageRepository s3ImageRepository;

    private final ObjectMapper objectMapper;

    private final EntityManagerFactory emf;

    @Async
    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void onMessage(String message) throws JsonProcessingException {
        DiaryCreatedEvent event = objectMapper.readValue(message, DiaryCreatedEvent.class);
        List<MultipartFile> imageFiles = event.getImageFiles();

        List<Image> images = s3ImageRepository.uploadOnS3();

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        diaryRepository.findById();
        entityManager.persist(diary);
    }
}
