package com.medinote.medinotebackspring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class S3Config {
    private static final Region region = Region.AP_NORTHEAST_2;

    public S3Client s3Client() {
        return S3Client.builder()
                .region(region)
                .build();
    }
}
