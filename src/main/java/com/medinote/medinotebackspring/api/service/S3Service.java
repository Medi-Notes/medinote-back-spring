package com.medinote.medinotebackspring.api.service;

import com.medinote.medinotebackspring.config.aws.S3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {
    @Value("${spring.cloud.aws.s3.bucket-name}")
    private String bucketName;
    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(String username, MultipartFile file, String filename) throws IOException {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(String.format("%s/%s", username, filename))
                .build();

        InputStream fileStream = file.getInputStream();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileStream,file.getSize()));
    }

    public byte[] getFile(String username, String filename) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(String.format("%s/%s",username,filename))
                .build();

//        s3.getObjectAsBytes(getObjectRequest);

        ResponseInputStream<GetObjectResponse> s3Response = s3Client.getObject(getObjectRequest);

//        ResponseBytes<GetObjectResponse> s3Bytes = s3.getObjectAsBytes(getObjectRequest);

        byte[] s3Bytes = s3Response.readAllBytes();

        return s3Bytes;
    }

    public InputStream getFileByInputStream(String username, String filename) throws IOException {
        String key = String.format("%s/%s",username,filename);

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

//        s3.getObjectAsBytes(getObjectRequest);

        ResponseInputStream<GetObjectResponse> s3Response = s3Client.getObject(getObjectRequest);

        return s3Response;
    }


}
