//package com.medinote.medinotebackspring.oauth.service;
//
//import com.medinote.medinotebackspring.api.entity.User;
//import com.medinote.medinotebackspring.api.repository.user.UserRepository;
//import com.medinote.medinotebackspring.oauth.entity.UserPrincipal;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUserId(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("Can not find username.");
//        }
//        return UserPrincipal.create(user);
//    }
//}
