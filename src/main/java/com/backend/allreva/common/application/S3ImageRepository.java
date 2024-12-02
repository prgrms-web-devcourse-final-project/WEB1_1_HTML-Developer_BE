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

@Slf4j
@RequiredArgsConstructor
@Service
public class S3ImageRepository implements ImageRepository{

    @Value("${spring.cloud.aws.s3.bucket:null}")
    private String bucketName;
    private final S3Operations s3Operations;

    @Override
    public Image uploadOnS3(
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
