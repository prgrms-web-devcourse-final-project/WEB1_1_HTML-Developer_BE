package com.backend.allreva.diary.command.application;

import com.backend.allreva.common.application.S3ImageService;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.diary.command.application.request.AddDiaryRequest;
import com.backend.allreva.diary.command.application.request.UpdateDiaryRequest;
import com.backend.allreva.diary.command.domain.ConcertDiary;
import com.backend.allreva.diary.command.domain.DiaryRepository;
import com.backend.allreva.diary.exception.DiaryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class DiaryCommandService {

    private final S3ImageService s3ImageService;
    private final DiaryRepository diaryRepository;

    public Long add(
            final AddDiaryRequest request,
            final List<MultipartFile> imageFiles,
            final Long memberId
    ) {
        ConcertDiary diary = request.to();
        List<Image> uploadedImages = s3ImageService.upload(imageFiles);

        diary.addImages(uploadedImages);
        diary.addMemberId(memberId);
        return diaryRepository.save(diary).getId();
    }

    public void update(
            final UpdateDiaryRequest request,
            final List<MultipartFile> imageFiles,
            final Long memberId
    ) {
        List<Image> uploadedImages = s3ImageService.upload(imageFiles);
        ConcertDiary diary = diaryRepository.findById(request.diaryId())
                .orElseThrow(DiaryNotFoundException::new);

        diary.validateWriter(memberId);
        diary.update(
                request.concertId(),
                request.date(),
                request.episode(),
                request.content(),
                request.seatName(),
                uploadedImages
        );
    }

    public void delete(final Long diaryId, final Long memberId) {
        ConcertDiary diary = diaryRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);
        diary.validateWriter(memberId);

        diaryRepository.deleteById(diaryId);
    }

    public void addImagesById(Long diaryId, List<Image> images) {
        ConcertDiary diary = diaryRepository.findById(diaryId)
                .orElseThrow();
        diary.addImages(images);
    }
}
