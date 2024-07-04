package com.medinote.medinotebackspring.api.service;

import com.medinote.medinotebackspring.config.S3Config;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${s3.bucket-name}")
    private String bucketName;
    private final S3Config s3Config;

    public void uploadFile(String username, MultipartFile file, String filename) throws IOException {
        S3Client s3 = s3Config.s3Client();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(String.format("%s/%s", username, filename))
                .build();

        InputStream fileStream = file.getInputStream();

        s3.putObject(putObjectRequest, RequestBody.fromInputStream(fileStream,file.getSize()));
    }

    public byte[] getFile(String username, String filename) throws IOException {
        S3Client s3 = s3Config.s3Client();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(String.format("%s/%s",username,filename))
                .build();

//        s3.getObjectAsBytes(getObjectRequest);

        ResponseInputStream<GetObjectResponse> s3Response = s3.getObject(getObjectRequest);

//        ResponseBytes<GetObjectResponse> s3Bytes = s3.getObjectAsBytes(getObjectRequest);

        byte[] s3Bytes = s3Response.readAllBytes();

        return s3Bytes;
    }

    public InputStream getFileByInputStream(String username, String filename) throws IOException {
        S3Client s3 = s3Config.s3Client();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(String.format("%s/%s",username,filename))
                .build();

//        s3.getObjectAsBytes(getObjectRequest);

        ResponseInputStream<GetObjectResponse> s3Response = s3.getObject(getObjectRequest);

        return s3Response;
    }


}
