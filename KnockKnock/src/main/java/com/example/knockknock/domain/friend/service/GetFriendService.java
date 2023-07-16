package com.example.knockknock.domain.friend.service;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.friend.repository.FriendRepository;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetFriendService {
    private final FriendRepository friendRepository;

    @Transactional(readOnly = true)
    public Friend getFriend(Long friendId){
        return friendRepository.findById(friendId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.FRIEND_NOT_FOUND)
        );
    }
}
