package com.medinote.medinotebackspring.openai;


import com.medinote.medinotebackspring.openai.request.GptRequest;
import com.medinote.medinotebackspring.openai.request.WhisperTranscriptionRequest;
import com.medinote.medinotebackspring.openai.response.GptResponse;
import com.medinote.medinotebackspring.openai.response.WhisperTranscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/*
    OpenAI API 호출
 */
@Component
@RequiredArgsConstructor
public class OpenAIClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final OpenAIProperties openAIProperties;


    /*
        STT 결과물을 Medinote로 변환하기 위해 "gpt API를 호출"하는 메서드
     */
    public GptResponse callGpt(GptRequest gptRequest) {
        String url = openAIProperties.fullGptUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openAIProperties.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GptRequest> request = new HttpEntity<>(gptRequest, headers);
        return restTemplate.postForObject(url, request, GptResponse.class);
    }

    public WhisperTranscriptionResponse callWhisper(MultiValueMap<String,Object> whisperRequest) {
        String url = openAIProperties.fullWhisperUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openAIProperties.getApiKey());
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(whisperRequest, headers);

        return restTemplate.postForObject(url, request, WhisperTranscriptionResponse.class);

    }


}
