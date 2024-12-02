package com.backend.allreva.diary.command.domain;

import com.backend.allreva.common.event.Event;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class DiaryCreatedEvent extends Event {

    private final Long diaryId;
    private final List<MultipartFile> imageFiles;

    @Builder
    public DiaryCreatedEvent(
            final Long diaryId,
            final List<MultipartFile> imageFiles
    ) {
        super();
        this.diaryId = diaryId;
        this.imageFiles = imageFiles;
    }
}
