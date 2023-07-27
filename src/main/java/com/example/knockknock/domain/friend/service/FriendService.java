package com.example.knockknock.domain.friend.service;

import com.example.knockknock.domain.friend.dto.requestDto.FriendRequestDto;
import com.example.knockknock.domain.friend.dto.responseDto.FriendDetailResponseDto;
import com.example.knockknock.domain.friend.dto.responseDto.FriendResponseDto;
import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.friend.repository.FriendRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.notification.dto.responseDto.NotificationResponseDto;
import com.example.knockknock.domain.notification.entity.Notification;
import com.example.knockknock.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final GetFriendService getFriendService;
    private final NotificationService notificationService;


    public List<FriendResponseDto> getFriends() {
        List<FriendResponseDto> friendResponseDtos = new ArrayList<>();
        // todo : memberId로 friend 찾아오기
        return friendResponseDtos;
    }

    @Transactional(readOnly = true)
    public FriendDetailResponseDto getDetailFriend(Long friendId/*, MemberDetailsImpl memberDetails*/) {
        // islogin
        Friend friend = getFriendService.getFriend(friendId);
        // 연락 일정 정보
        Notification notification = notificationService.getNotificationSchedule(friendId);
        return new FriendDetailResponseDto(friend, notification);
    }

    public void updateFriendInfo(Long friendId, FriendRequestDto friendRequestDto/*, MemberDetailsImpl memberDetails*/) {
        // isLogin

        Friend friend = getFriendService.getFriend(friendId);
        // checkRole

        // setter 안쓰는 방법 생각해보기
        if (friendRequestDto.getFriendName() != null) friend.setFriendName(friendRequestDto.getFriendName());
        if (friendRequestDto.getNickName() != null) friend.setNickname(friendRequestDto.getNickName());
        if (friendRequestDto.getPhoneNumber() != null) friend.setPhoneNumber(friendRequestDto.getPhoneNumber());

        // 원래 있던 프로필이미지 지우고 올리기
        MultipartFile profileImage = friendRequestDto.getProfileImage();
        String profileImageURL = null;

        // profileImageURL 구현

    }

    @Transactional
    public void deleteFriend(Long friendId) {
        // isLogin
        // 연관관계 정리
        friendRepository.deleteById(friendId);
    }

    // 찐친 등록
    public void updateBestFriendStatus(Long friendId) {
        Friend friend = getFriendService.getFriend(friendId);
        friend.setBestFriend();
    }

    @Transactional
    public void addFriends(FriendRequestDto friendRequestDto) {
        // isLogin
        // 이름(필수), 별명(선택), 전화번호(선택)
        String profileImageURL = null;
        Friend friend = new Friend(friendRequestDto, profileImageURL);
        friendRepository.save(friend);
    }
}
