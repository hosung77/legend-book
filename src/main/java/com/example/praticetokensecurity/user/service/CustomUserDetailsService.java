package com.example.praticetokensecurity.user.service;

import com.example.praticetokensecurity.common.exception.CustomException;
import com.example.praticetokensecurity.common.exception.ErrorCode;
import com.example.praticetokensecurity.user.entity.CustomUserPrincipal;
import com.example.praticetokensecurity.user.entity.User;
import com.example.praticetokensecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(()-> new CustomException(ErrorCode.EMAIL_NOT_FOUND));

        return new CustomUserPrincipal(user);

    }
}
