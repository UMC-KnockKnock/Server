package com.example.knockknock.service;

import com.example.knockknock.domain.model.User;
import com.example.knockknock.domain.repository.UserRepository;
import com.example.knockknock.exception.NotMatchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserFromToken() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new NotMatchUserException("토큰 정보와 일치하는 회원 정보가 없습니다."));
    }

}
