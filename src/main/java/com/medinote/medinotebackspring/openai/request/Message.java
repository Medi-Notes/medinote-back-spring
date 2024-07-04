package com.medinote.medinotebackspring.openai.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    public String role;
    public String content;
}
