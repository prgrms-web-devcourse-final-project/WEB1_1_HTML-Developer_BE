package com.backend.allreva.common.application;

import com.backend.allreva.member.command.application.ImageService;
import io.awspring.cloud.s3.S3Operations;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3ImageService implements ImageService {

    @Value("${spring.cloud.aws.s3.bucket:null}")
    private String bucketName;

    private final S3Operations s3Operations;
}
