package com.medinote.medinotebackspring.api.controller;

import com.medinote.medinotebackspring.api.entity.User;
import com.medinote.medinotebackspring.api.service.SQSService;
import com.medinote.medinotebackspring.api.service.UserService;
import com.medinote.medinotebackspring.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/sqs")
@RequiredArgsConstructor
public class SQSController {
    private final SQSService sqsService;
    private final UserService userService;

    @PostMapping("send")
    public ApiResponse<Object> sendTransformRequest(@RequestBody Map<String,String> sqsMap) {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(principal.getUsername());

        String filename = sqsMap.get("filename");
        String message = String.format("%s@%s",user.getUserId(),filename);

        try {
            sqsService.sendMessage(message);

            return ApiResponse.success();
        } catch (Exception e) {

            return ApiResponse.fail("errorMessage",e);
        }

    }
}
