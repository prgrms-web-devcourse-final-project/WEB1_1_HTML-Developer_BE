package com.backend.allreva.common.application;

import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.common.exception.code.GlobalErrorCode;
import com.backend.allreva.common.model.Image;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3ImageService {

    @Value("${spring.cloud.aws.s3.bucket:null}")
    private String bucketName;
    private final S3Operations s3Operations;

    public List<Image> upload(List<MultipartFile> imageFiles) {
        if (imageFiles == null || imageFiles.isEmpty()) {
            return Collections.emptyList();
        }
        List<Image> images = new ArrayList<>();

        for (MultipartFile file : imageFiles) {
            Image uploadedImage = upload(file);
            images.add(uploadedImage);
        }
        return images;
    }

    public Image upload(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return new Image("");
        }
        ObjectMetadata objectMetadata = new ObjectMetadata.Builder()
                .contentType(imageFile.getContentType())
                .build();
        String storeKey = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        return uploadOnS3(imageFile, storeKey, objectMetadata);
    }

    private Image uploadOnS3(
            MultipartFile imageFile,
            String storeKey,
            ObjectMetadata metadata
    ) {
        try (InputStream inputStream = imageFile.getInputStream()) {
            S3Resource resource = s3Operations.upload(bucketName, storeKey, inputStream, metadata);
            return new Image(resource.getURL().toString());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new CustomException(GlobalErrorCode.SERVER_ERROR);
        }
    }
}
