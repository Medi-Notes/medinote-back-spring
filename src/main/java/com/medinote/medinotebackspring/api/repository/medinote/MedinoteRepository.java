package com.medinote.medinotebackspring.api.repository.medinote;

import com.medinote.medinotebackspring.api.entity.Medinote;
import com.medinote.medinotebackspring.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedinoteRepository extends JpaRepository<Medinote, Long> {
//    Medinote findByUserIdAndMedinoteTitle(User userId, String medinoteTitle);
    Medinote findByMedinoteTitle(String medinoteTitle);
    Medinote findByMedinoteSeq(Long medinoteSeq);

    List<Medinote> findMedinotesByUserId(User userId);
}
