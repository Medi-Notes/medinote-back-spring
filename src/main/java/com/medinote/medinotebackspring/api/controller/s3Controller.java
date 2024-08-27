package com.medinote.medinotebackspring.api.controller;

import com.medinote.medinotebackspring.api.entity.Medinote;
import com.medinote.medinotebackspring.api.entity.MedinoteMetadata;
import com.medinote.medinotebackspring.api.entity.User;
import com.medinote.medinotebackspring.api.repository.medinote.MedinoteMetadataRepository;
import com.medinote.medinotebackspring.api.service.MedinoteMetadataService;
import com.medinote.medinotebackspring.api.service.MedinoteService;
import com.medinote.medinotebackspring.api.service.S3Service;
import com.medinote.medinotebackspring.api.service.UserService;
import com.medinote.medinotebackspring.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/s3")
@RequiredArgsConstructor
public class s3Controller {
    private final UserService userService;
    private final S3Service s3Service;
    private final MedinoteService medinoteService;
    private final MedinoteMetadataService medinoteMetadataService;

    private static final String UUID_DIVEDER = "___";

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Object> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(principal.getUsername());

        String filename = file.getOriginalFilename();
        String uuidFilename = UUID.randomUUID() + UUID_DIVEDER + filename;


        try {
            s3Service.uploadFile(user.getUserId(), file, uuidFilename);

            // 타이틀은 medinote 테이블에 업로드
            Medinote newMedinote = new Medinote(filename,user);
            medinoteService.putMedinote(newMedinote);

            // uuid가 붙은 파일 이름은 해당 이름으로 S3에 업로드 되고 metadata 테이블에 저장 되어야 함
            medinoteMetadataService.putMedinoteMetadata(
                    new MedinoteMetadata(newMedinote, uuidFilename)
            );

            return ApiResponse.success("filename", uuidFilename);
        } catch (Exception e) {
            return ApiResponse.fail("message",e.getMessage());
        }


//        log.error("Test UUID: " + uuid);

//        s3Service.uploadFile("jiyun", file, uuidFilename);

//        String filename = file.getOriginalFilename();
//        String medinoteTitle = filename;
//        if (filename != null && filename.endsWith(".mp3")) {
//            medinoteTitle = filename.replace(".mp3", "");
//        }
//        // 타이틀은 medinote 테이블에 업로드
//        Medinote newMedinote = new Medinote(filename);
//        medinoteService.putMedinote(newMedinote);
//
//        // uuid가 붙은 파일 이름은 해당 이름으로 S3에 업로드 되고 metadata 테이블에 저장 되어야 함
//        medinoteMetadataService.putMedinoteMetadata(
//                new MedinoteMetadata(newMedinote, uuidFilename)
//        );


//        return ApiResponse.success("filename", uuidFilename);
    }
}
