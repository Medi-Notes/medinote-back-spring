package com.medinote.medinotebackspring.api.controller;

import com.medinote.medinotebackspring.api.entity.Medinote;
import com.medinote.medinotebackspring.api.entity.MedinoteMetadata;
import com.medinote.medinotebackspring.api.service.MedinoteMetadataService;
import com.medinote.medinotebackspring.api.service.MedinoteService;
import com.medinote.medinotebackspring.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/medinote")
@RequiredArgsConstructor
public class MedinoteController {
    private final MedinoteService medinoteService;
    private final MedinoteMetadataService medinoteMetadataService;

    @PostMapping("/upload")
    public ApiResponse<Object> uploadMedinote() {
        Medinote newMedinote = new Medinote();
//        newMedinote.setUserId(new User());
        newMedinote.setMedinoteTitle("테스트 제목 1111");
        newMedinote.setCreatedAt(LocalDateTime.now());
        newMedinote.setModifiedAt(LocalDateTime.now());
        newMedinote.setTransformFin("N");
        medinoteService.putMedinote(newMedinote);


        MedinoteMetadata newMedinoteMetadata = new MedinoteMetadata();
        newMedinoteMetadata.setMedinote(newMedinote);
        newMedinoteMetadata.setAudioFilename("테스트 음성파일 11");
        medinoteMetadataService.putMedinoteMetadata(newMedinoteMetadata);

        return ApiResponse.success();
    }

    @GetMapping
    public Medinote retrieveMedinoteByTitle() {
        Medinote note = medinoteService.getNoteInfo("테스트 제목 1111");

        return note;
    }

    @GetMapping("/{id}")
    public Medinote retrieveMedinoteBySeq(@PathVariable Long id) {
        Medinote note = medinoteService.getNoteBySeq(id);

        return note;
    }

    @GetMapping("/meta/{id}")
    public MedinoteMetadata retrieveMetadatBySeq(@PathVariable Long id) {
        Medinote note = medinoteService.getNoteBySeq(id);

        return medinoteMetadataService.getMetadataBySeq(note);
    }

}
