package com.medinote.medinotebackspring.api.service;

import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SQSService {
    private final SqsTemplate template;
    @Value("${spring.cloud.aws.sqs.queue-name}")
    private String queueName;

    /*
     "USERID@FILENAME" 포맷으로 메시지 전송
     */
    public SendResult<String> sendMessage(String message) {
        return template.send(to -> to
                .queue(queueName)
                .payload(message));
    }

}
