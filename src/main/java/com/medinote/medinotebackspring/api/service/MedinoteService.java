package com.medinote.medinotebackspring.api.service;


import com.medinote.medinotebackspring.api.entity.Medinote;
import com.medinote.medinotebackspring.api.entity.User;
import com.medinote.medinotebackspring.api.repository.medinote.MedinoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedinoteService {
    private final MedinoteRepository medinoteRepository;

    public Medinote getNoteInfo(String title) {
        return medinoteRepository.findByMedinoteTitle(title);
    }
//    public Medinote getNoteInfo(User userId, String title) {
//        return medinoteRepository.findByUserIdAndMedinoteTitle(userId,title);
//    }

    public Medinote getNoteBySeq(Long seq) {
        return medinoteRepository.findByMedinoteSeq(seq);
    }

    public void putMedinote(Medinote medinote) {
        medinoteRepository.save(medinote);
    }
}
