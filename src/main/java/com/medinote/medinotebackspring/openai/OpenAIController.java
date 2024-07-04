package com.medinote.medinotebackspring.openai;

import com.medinote.medinotebackspring.api.entity.Medinote;
import com.medinote.medinotebackspring.api.entity.MedinoteMetadata;
import com.medinote.medinotebackspring.api.service.MedinoteMetadataService;
import com.medinote.medinotebackspring.api.service.MedinoteService;
import com.medinote.medinotebackspring.api.service.S3Service;
import com.medinote.medinotebackspring.api.service.UserService;
import com.medinote.medinotebackspring.common.ApiResponse;
import com.medinote.medinotebackspring.openai.response.GptResponse;
import com.medinote.medinotebackspring.openai.response.WhisperTranscriptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/openai")
@RequiredArgsConstructor
public class OpenAIController {
    private final OpenAIApiService openAIApiService;
    private final UserService userService;
    private final S3Service s3Service;
    private final MedinoteService medinoteService;
    private final MedinoteMetadataService medinoteMetadataService;

    /* 테스트용 API */
    @GetMapping("/gpt")
    public ApiResponse<Object> testGPT(@RequestBody Map<String, String> promptMap) {
        GptResponse response = openAIApiService.getGptResponse(promptMap.get("prompt"));

        return ApiResponse.success("message", response.getChoices().get(0).getMessage().getContent());
    }


    /*
    rawText를 받아 Medinote로 변환하는 메서드
    나중에 medinote_seq로 받아 Medinote_metadata 테이블에서 STT_TEXT 읽어와서 GPT 호출 메서드도 추가 필요
     */
//    @PostMapping("/text2note")
//    public ApiResponse<Object> textToNote(@RequestBody Map<String, String> textMap) {
//        GptResponse response = openAIApiService.getTextToNoteResponse(textMap.get("rawText"));
//
//        String medinoteText = response.getChoices().get(0).getMessage().getContent();
//
//
//
//        return ApiResponse.success("medinoteText", medinoteText);
//    }

    @PostMapping("/text2note")
    public ApiResponse<Object> textToNote(@RequestBody Map<String, String> filenameMap) {
        String filename = filenameMap.get("filename");
        MedinoteMetadata metadata = medinoteMetadataService.getMetadataByAudioFilename(filename);

        GptResponse response = openAIApiService.getTextToNoteResponse(metadata.getSttText());
        String medinoteText = response.getChoices().get(0).getMessage().getContent();

        Medinote medinote = medinoteService.getNoteBySeq(metadata.getMedinoteSeq());
        medinote.setMedinoteText(medinoteText);
        medinote.setTransformFin("Y");
        medinoteService.putMedinote(medinote);

        return ApiResponse.success("medinoteText", medinoteText);
    }

    /*
    API 호출 시 파일을 보내는 방식 (아마 쓸일 없을 듯)
     */
    @PostMapping(value = "/audio2text", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Object> audioToText(@RequestParam("file") MultipartFile file) throws IOException {

        log.error("!!!!! file name: " + file);
//        WhisperTranscriptionResponse response = openAIApiService.getAudioToTextResponse(file);
//        WhisperTranscriptionResponse response = openAIApiService.getAudioToTextResponse(file.getInputStream(), file.getOriginalFilename(), file.getSize());
        WhisperTranscriptionResponse response = openAIApiService.getAudioToTextResponse(file.getBytes(), file.getOriginalFilename());

        return ApiResponse.success("text", response.getText());
    }

    /*
    API 호출 시 파일 이름을 불러 s3에서 로드하는 방식
    나중에 medinote_seq로도 호출하는 메서드 추가 필요 -> 이 경우 seq 리턴 해주는 방식 사용해야 될 듯
     */
    @PostMapping(value = "/audio2text")
    public ApiResponse<Object> audioToText(@RequestBody Map<String, String> filenameMap) throws IOException {
        String filename  = filenameMap.get("filename");

        /*
        byte가 아니라 inputstream으로 바꿔야 될듯!!! (ok)
        나중에 userid로 변경 해야 됨
         */
        InputStream audioFile = s3Service.getFileByInputStream("jiyun",filename);


//        WhisperTranscriptionResponse response = openAIApiService.getAudioToTextResponse(file);
//        WhisperTranscriptionResponse response = openAIApiService.getAudioToTextResponse(file.getInputStream(), file.getOriginalFilename(), file.getSize());
        WhisperTranscriptionResponse response = openAIApiService.getAudioToTextResponse(audioFile,filename);

        String sttText = response.getText();

        MedinoteMetadata metadata = medinoteMetadataService.getMetadataByAudioFilename(filename);
        metadata.setSttText(sttText);

        medinoteMetadataService.putMedinoteMetadata(metadata);

//        log.error(metadata.getMedinoteSeq()+ " / "+ metadata.getAudioFilename());

        return ApiResponse.success("metadata", metadata);
    }

//    @PostMapping(value = "/audio2text")
//    public ApiResponse<Object> audioToText(@RequestParam("file") String fileName) {
//
//        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userService.getUser(principal.getUsername());
//
//        String username = user.getUserId();
//
////        s3Service.GetFile();
//
//
//        WhisperTranscriptionResponse response = openAIApiService.getAudioToTextResponse(file);
//
//        return ApiResponse.success("text", response.getText());
//    }
}
