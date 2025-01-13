package com.backend.allreva.seat_review.command.application;

import com.backend.allreva.common.application.S3ImageService;
import com.backend.allreva.common.event.EntityType;
import com.backend.allreva.common.event.Event;
import com.backend.allreva.common.event.EventEntryRepository;
import com.backend.allreva.common.event.deadletter.DeadLetterHandler;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.seat_review.command.domain.SeatReviewImage;
import com.backend.allreva.seat_review.exception.SeatReviewImageSaveFailedException;
import com.backend.allreva.seat_review.infra.SeatReviewImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatReviewImageEventHandler {
    private final SeatReviewImageRepository seatReviewImageRepository;
    private final S3ImageService s3ImageService;
    private final EventEntryRepository eventEntryRepository;
    private final DeadLetterHandler deadLetterHandler;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onMessage(final SeatReviewImageEvent event) {
        if (isEventExpired(event)) {
            log.info("이벤트가 만료됨. seatReviewId: {}", event.getSeatReviewId());
            return;
        }

        List<Image> uploadedImages = new ArrayList<>();
        try {
            uploadedImages = uploadImages(event.getImages());
            saveSeatReviewImages(event.getSeatReviewId(), uploadedImages);
            log.info("SeatReviewImageEvent Sync 완료!! seatReviewId: {}", event.getSeatReviewId());
        } catch (Exception e) {
            rollbackImages(uploadedImages);
            deadLetterHandler.put(event);
            log.info("SeatReviewImageEvent가 DeadLetterQueue로 발송 성공!! seatReviewId: {}", event.getSeatReviewId());
            throw new SeatReviewImageSaveFailedException();
        }
    }

    private List<Image> uploadImages(List<MultipartFile> images) {
        List<CompletableFuture<Image>> futures = images.stream()
                .map(this::uploadImageAsync)
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    private void saveSeatReviewImages(Long seatReviewId, List<Image> uploadedImages) {
        List<SeatReviewImage> seatReviewImages = uploadedImages.stream()
                .map(img -> SeatReviewImage.builder()
                        .url(img.getUrl())
                        .seatReviewId(seatReviewId)
                        .build())
                .toList();

        seatReviewImageRepository.saveAll(seatReviewImages);
    }

    private void rollbackImages(List<Image> uploadedImages) {
        uploadedImages.forEach(image -> deleteImageAsync(image.getUrl()));
    }

    @Async("taskExecutor")
    public CompletableFuture<Image> uploadImageAsync(final MultipartFile image) {
        return CompletableFuture.completedFuture(s3ImageService.upload(image));
    }

    @Async("taskExecutor")
    public void deleteImageAsync(final String imageUrl) {
        s3ImageService.delete(imageUrl);
    }

    private boolean isEventExpired(final SeatReviewImageEvent event) {
        return isEventExpired(event.getSeatReviewId(), event);
    }

    private boolean isEventExpired(final Long seatReviewId, final Event event) {
        if (event.isReissued()) {
            return !eventEntryRepository.isValidEvent(
                    EntityType.SEAT_REVIEW_IMAGE,
                    seatReviewId.toString(),
                    event.getTimestamp()
            );
        }

        int affectedRows = eventEntryRepository.upsert(
                EntityType.SEAT_REVIEW_IMAGE.name(),
                seatReviewId.toString(),
                event.getTimestamp()
        );
        return affectedRows == 0;
    }
}
