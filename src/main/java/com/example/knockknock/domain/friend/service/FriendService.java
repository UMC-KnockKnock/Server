package com.example.knockknock.domain.friend.service;

import com.example.knockknock.domain.friend.dto.requestDto.FriendRequestDto;
import com.example.knockknock.domain.friend.dto.responseDto.FriendDetailResponseDto;
import com.example.knockknock.domain.friend.dto.responseDto.FriendResponseDto;
import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.friend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final GetFriendService getFriendService;


    public List<FriendResponseDto> getFriends() {
        List<FriendResponseDto> friendResponseDtos = new ArrayList<>();
        // memberId로 friend 찾아오기
        return friendResponseDtos;
    }

    @Transactional(readOnly = true)
    public FriendDetailResponseDto getDetailFriend(Long friendId/*, MemberDetailsImpl memberDetails*/) {
        Friend friend = getFriendService.getFriend(friendId);

        // 찐친 여부
        boolean isBestFriend = friend.setBestFriend();

        // 연락 일정 정보
        // Notification notification =

//        return new FriendDetailResponseDto(friend, isBestFriend, notification);
        return new FriendDetailResponseDto();
    }

    public void updateFriendInfo(Long friendId, FriendRequestDto friendRequestDto/*, MemberDetailsImpl memberDetails*/) {
        // isLogin

        Friend friend = getFriendService.getFriend(friendId);
        // checkRole

        // profileImageURL

//        friend.update(friendRequestDto, profileImageURL);

    }

    public void updateBestfriendStatus(Long friendId) {
        Friend friend = getFriendService.getFriend(friendId);
        friend.setBestFriend();
    }
}
