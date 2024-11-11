package com.medinote.medinotebackspring.api.service;

import com.medinote.medinotebackspring.api.entity.Medinote;
import com.medinote.medinotebackspring.api.entity.MedinoteMetadata;
import com.medinote.medinotebackspring.openai.OpenAIApiService;
import com.medinote.medinotebackspring.openai.response.WhisperTranscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedinoteTransformService {
    private final MedinoteService medinoteService;
    private final MedinoteMetadataService medinoteMetadataService;
    private final OpenAIApiService openAIApiService;
    private final S3Service s3Service;


    @Transactional
    public String textToNote(String filename) {
        MedinoteMetadata metadata = medinoteMetadataService.getMetadataByAudioFilename(filename);
        String medinoteText = openAIApiService.getTextToNoteResponse(metadata.getSttText())
                .getChoices().get(0).getMessage().getContent();

        Medinote medinote = medinoteService.getNoteBySeq(metadata.getMedinoteSeq());
        medinote.setMedinoteText(medinoteText);
        medinote.setTransformFin("Y");

        return medinoteText;
    }

    @Transactional
    public String audioToText(String filename, String userId) throws IOException {
        InputStream audioFile = s3Service.getFileByInputStream(userId, filename);

        String sttText = openAIApiService
                .getAudioToTextResponse(audioFile,filename)
                .getText();

        MedinoteMetadata metadata = medinoteMetadataService.getMetadataByAudioFilename(filename);
        metadata.setSttText(sttText);
        medinoteMetadataService.putMedinoteMetadata(metadata);

        return sttText;
    }
}
