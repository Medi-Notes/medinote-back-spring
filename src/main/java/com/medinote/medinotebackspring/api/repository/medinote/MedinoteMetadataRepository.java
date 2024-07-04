package com.medinote.medinotebackspring.api.repository.medinote;

import com.medinote.medinotebackspring.api.entity.Medinote;
import com.medinote.medinotebackspring.api.entity.MedinoteMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedinoteMetadataRepository extends JpaRepository<MedinoteMetadata, Long> {
    MedinoteMetadata getByMedinoteSeq(Long medinoteSeq);
    MedinoteMetadata getByAudioFilename(String audioFilename);

}
