package com.backend.allreva.common.application;

import com.backend.allreva.common.model.Image;
import io.awspring.cloud.s3.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private String bucketName;
    private final ImageRepository imageRepository;

    public Image upload(List<MultipartFile> imageFiles) {
        if (imageFiles != null && !imageFiles.isEmpty()) {
            imageFiles.forEach(this::upload);
        }
    }

    public Image upload(MultipartFile imageFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata.Builder()
                .contentType(imageFile.getContentType())
                .build();
        String storeKey = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        imageRepository.uploadOnS3(imageFile, storeKey, objectMetadata);
    }
}
