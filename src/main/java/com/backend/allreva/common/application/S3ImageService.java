package com.backend.allreva.common.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.backend.allreva.member.command.application.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3ImageService implements ImageService {

    private final AmazonS3Client s3Client;
}
