package com.medinote.medinotebackspring.api.service;

import com.medinote.medinotebackspring.api.entity.Medinote;
import com.medinote.medinotebackspring.api.entity.MedinoteMetadata;
import com.medinote.medinotebackspring.api.repository.medinote.MedinoteMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedinoteMetadataService {
    private final MedinoteMetadataRepository medinoteMetadataRepository;

    public MedinoteMetadata getMetadataBySeq(Medinote note) {
        return medinoteMetadataRepository.getByMedinoteSeq(note.getMedinoteSeq());
    }

    public MedinoteMetadata getMetadataByAudioFilename(String audioFilename) {
        return medinoteMetadataRepository.getByAudioFilename(audioFilename);
    }

    public void putMedinoteMetadata(MedinoteMetadata meta) {
        medinoteMetadataRepository.save(meta);
    }

}
