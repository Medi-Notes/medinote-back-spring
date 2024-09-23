package com.medinote.medinotebackspring;

import com.medinote.medinotebackspring.config.aws.S3Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class S3ServiceTest {
    @Value("${s3.bucket-name}")
    private String bucketName;
    private final S3Config s3Config = new S3Config();


//    @Test
//    void getFileTest() {
//        String username = "jiyun";
//        String filename = "940383cf-3f53-4120-8d9b-12ec56c35af4___medinote example audio.mp3";
//
//        S3Client s3 = s3Config.s3Client();
//
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(String.format("%s/%s",username,filename))
//                .build();
//
//        ResponseInputStream<GetObjectResponse> s3Response = s3.getObject(getObjectRequest);
//
//
////        s3.getObjectAsBytes(getObjectRequest);
//
////        ResponseBytes<GetObjectResponse> s3ObjectBytes = s3.getObjectAsBytes(getObjectRequest);
//
//        try {
//            // Create a file and write the data to it
//            File localFile = new File("testfile.mp3");
//            try (FileOutputStream fos = new FileOutputStream(localFile)) {
//                fos.write(s3Response.readAllBytes());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
