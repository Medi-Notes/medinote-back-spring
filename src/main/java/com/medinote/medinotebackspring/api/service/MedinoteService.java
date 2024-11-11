package com.medinote.medinotebackspring.api.service;


import com.medinote.medinotebackspring.api.entity.Medinote;
import com.medinote.medinotebackspring.api.entity.User;
import com.medinote.medinotebackspring.api.repository.medinote.MedinoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedinoteService {
    private final MedinoteRepository medinoteRepository;

    public List<Medinote> getMedinotes(User userId) {
        return medinoteRepository.findMedinotesByUserId(userId);
    }
//    public Medinote getNoteInfo(User userId, String title) {
//        return medinoteRepository.findByUserIdAndMedinoteTitle(userId,title);
//    }

    public Medinote getNoteBySeq(Long seq) {
        return medinoteRepository.findByMedinoteSeq(seq);
    }

    @Transactional
    public void putMedinote(Medinote medinote) {
        medinoteRepository.save(medinote);
    }

    @Transactional
    public void deleteMedinote(Long medinoteSeq) {
        medinoteRepository.delete(
                medinoteRepository.findByMedinoteSeq(medinoteSeq)
        );
    }

    @Transactional
    public void deleteMedinotes(List<Long> seqs) {
        for (Long seq : seqs) {
            deleteMedinote(seq);
        }
    }
}
