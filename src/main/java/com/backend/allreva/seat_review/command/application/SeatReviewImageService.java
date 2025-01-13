package com.backend.allreva.seat_review.command.application;

import com.backend.allreva.common.application.S3ImageService;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.seat_review.command.domain.SeatReviewImage;
import com.backend.allreva.seat_review.exception.SeatReviewImageSaveFailedException;
import com.backend.allreva.seat_review.infra.SeatReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatReviewImageService {
    private final SeatReviewImageRepository seatReviewImageRepository;
    private final S3ImageService s3ImageService;

    public void saveImages(
            final List<MultipartFile> images,
            final Long seatReviewId
    ) {
        List<Image> uploadedImages = new ArrayList<>();
        try {
            List<CompletableFuture<Image>> futures = images.stream()
                    .map(this::uploadImage)
                    .toList();

            uploadedImages = futures.stream()
                    .map(CompletableFuture::join)
                    .toList();

            List<SeatReviewImage> seatReviewImages = uploadedImages.stream()
                    .map(img -> SeatReviewImage.builder()
                            .url(img.getUrl())
                            .seatReviewId(seatReviewId)
                            .build())
                    .toList();

            seatReviewImageRepository.saveAll(seatReviewImages);
        } catch (Exception e) {
            // 실패시 업로드된 이미지 삭제 처리
            uploadedImages.forEach(image ->
                    s3ImageService.delete(image.getUrl())
            );
            throw new SeatReviewImageSaveFailedException();
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<Image> uploadImage(final MultipartFile image) {
        return CompletableFuture.completedFuture(s3ImageService.upload(image));
    }

    @Async("taskExecutor")
    public void deleteImage(final String imageUrl){
        s3ImageService.delete(imageUrl);
    }
}
