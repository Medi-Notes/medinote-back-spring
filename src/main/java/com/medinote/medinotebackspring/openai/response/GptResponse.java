package com.medinote.medinotebackspring.openai.response;

import com.medinote.medinotebackspring.openai.request.Message;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GptResponse implements Serializable {
    private List<Choice> choices;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice {
        private int index;
        private Message message;
    }
}
