package com.medinote.medinotebackspring.api.service;

import com.medinote.medinotebackspring.api.entity.User;
import com.medinote.medinotebackspring.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}