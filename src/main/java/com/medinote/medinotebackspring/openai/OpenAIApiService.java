package com.medinote.medinotebackspring.openai;

import com.medinote.medinotebackspring.openai.request.Message;
import com.medinote.medinotebackspring.openai.request.GptRequest;
import com.medinote.medinotebackspring.openai.request.WhisperTranscriptionRequest;
import com.medinote.medinotebackspring.openai.response.GptResponse;
import com.medinote.medinotebackspring.openai.response.WhisperTranscriptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/*
    호출할 OpenAI API 에 필요한 정보 다루기
    ex) Prompt, Text, Audio File
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIApiService {
    private final OpenAIClient openAIClient;
    private final OpenAIProperties openAIProperties;

    /*
        prompt를 그냥 gpt한테 전송하는 메서드
     */
    public GptResponse getGptResponse(String prompt) {
        GptRequest gptRequest = new GptRequest();
        gptRequest.setModel(openAIProperties.getGptModel());
        gptRequest.setMessages(List.of(new Message("user", prompt)));

        return openAIClient.callGpt(gptRequest);
    }

    /*
        실제로 sttText를 Prompt에 넣어 gpt 호출한 결과를 리턴하는 메서드
     */
    public GptResponse getTextToNoteResponse(String sttText) {
        GptRequest gptRequest = new GptRequest();
        gptRequest.setModel(openAIProperties.getGptModel());
        gptRequest.setMessages(
                List.of(
                        new Message("system", openAIProperties.textToNoteSysPrompt()),
                        new Message("user", openAIProperties.textToNotePrompt(sttText))
                )
        );

        return openAIClient.callGpt(gptRequest);
    }

    public WhisperTranscriptionResponse getAudioToTextResponse(MultipartFile audioFile) {
//        WhisperTranscriptionRequest whisperRequest = new WhisperTranscriptionRequest();
//        whisperRequest.setModel(openAIProperties.getAudioModel());
//        whisperRequest.setFile(audioFile);
        MultiValueMap<String, Object> whisperRequest = new LinkedMultiValueMap<>();

        whisperRequest.add("model", openAIProperties.getAudioModel());
        whisperRequest.add("file", audioFile.getResource());

        return openAIClient.callWhisper(whisperRequest);
    }

    public WhisperTranscriptionResponse getAudioToTextResponse(InputStream audioStream, String filename) {
//        WhisperTranscriptionRequest whisperRequest = new WhisperTranscriptionRequest();
//        whisperRequest.setModel(openAIProperties.getAudioModel());
//        whisperRequest.setFile(audioFile);
        MultiValueMap<String, Object> whisperRequest = new LinkedMultiValueMap<>();

        whisperRequest.add("model", openAIProperties.getAudioModel());
        whisperRequest.add("file", new InputStreamResource(audioStream){
            @Override
            public String getFilename() {
                return filename;
            }

            @Override
            public long contentLength() throws IOException {
                return audioStream.available();
            }
        });

        return openAIClient.callWhisper(whisperRequest);
    }

    public WhisperTranscriptionResponse getAudioToTextResponse(byte[] audioBytes, String filename) {
//        WhisperTranscriptionRequest whisperRequest = new WhisperTranscriptionRequest();
//        whisperRequest.setModel(openAIProperties.getAudioModel());
//        whisperRequest.setFile(audioFile);
        MultiValueMap<String, Object> whisperRequest = new LinkedMultiValueMap<>();

        whisperRequest.add("model", openAIProperties.getAudioModel());
        whisperRequest.add("file", new ByteArrayResource(audioBytes) {
            @Override
            public String getFilename() {
                return filename;
            }
        });

        return openAIClient.callWhisper(whisperRequest);
    }

}
