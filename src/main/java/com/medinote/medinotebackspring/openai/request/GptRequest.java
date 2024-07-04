package com.medinote.medinotebackspring.openai.request;

import lombok.*;

import java.util.List;

@Data
public class GptRequest {
    private String model;
    private List<Message> messages;
}
