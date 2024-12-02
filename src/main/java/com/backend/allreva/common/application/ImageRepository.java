package com.backend.allreva.common.application;


import com.backend.allreva.common.model.Image;
import io.awspring.cloud.s3.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

public interface ImageRepository {
    Image uploadOnS3(
            MultipartFile imageFile,
            String storeKey,
            ObjectMetadata metadata
    );
}
