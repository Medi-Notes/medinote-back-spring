package com.medinote.medinotebackspring.api.controller;

import com.medinote.medinotebackspring.api.entity.Medinote;
import com.medinote.medinotebackspring.api.entity.MedinoteMetadata;
import com.medinote.medinotebackspring.api.entity.User;
import com.medinote.medinotebackspring.api.service.MedinoteMetadataService;
import com.medinote.medinotebackspring.api.service.MedinoteService;
import com.medinote.medinotebackspring.api.service.UserService;
import com.medinote.medinotebackspring.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/medinote")
@RequiredArgsConstructor
public class MedinoteController {
    private final MedinoteService medinoteService;
    private final MedinoteMetadataService medinoteMetadataService;
    private final UserService userService;

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
    public List<Medinote> retrieveMedinotesByUserId() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(principal.getUsername());

        List<Medinote> medinotes = medinoteService.getMedinotes(user);

        return medinotes;
    }

    @DeleteMapping("/delete")
    public ApiResponse<Object> deleteMedinoteBySeqs(@RequestBody Map<String, List<Long>> seqsMap) {
        List<Long> medinoteSeqs = seqsMap.get("medinoteSeqs");
        System.out.println(medinoteSeqs);
        medinoteService.deleteMedinotes(medinoteSeqs);

        return ApiResponse.success();
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
