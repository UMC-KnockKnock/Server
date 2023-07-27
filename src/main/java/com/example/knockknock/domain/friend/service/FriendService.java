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
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final GetFriendService getFriendService;
    private final NotificationService notificationService;

    @Transactional
    public void createFriends(FriendRequestDto friendRequestDto, UserDetailsImpl userDetails) {
        // isLogin
        Member member = isLogin(userDetails);
        // 해당 member 정보도 friend에 저장
        String profileImageURL = null;
        Friend friend = new Friend(friendRequestDto, profileImageURL, member);
        friendRepository.save(friend);
    }

    public void contactFriend(List<FriendRequestDto> friendRequestDtos, UserDetailsImpl userDetails) {
        // isLogin
        Member member = isLogin(userDetails);

        // 해당 member 친구 목록 가져오기 (Map 형태로 변경)
        Map<String, Friend> friendsMap = friendRepository.findAllByMember(member)
                .stream()
                .collect(Collectors.toMap(Friend::getPhoneNumber, friend -> friend));

        // 친구 목록 돌면서 friendRequestDtos에 없는 애들은 삭제
        /*friendsMap.entrySet().removeIf(entry -> !friendRequestDtos.stream()
                .anyMatch(dto -> dto.getPhoneNumber().equals(entry.getKey())));*/
        List<Friend> friendsToDelete = friendsMap.values().stream()
                .filter(friend -> friendRequestDtos.stream()
                        .noneMatch(dto -> dto.getPhoneNumber().equals(friend.getPhoneNumber())))
                .collect(Collectors.toList());
        friendRepository.deleteAll(friendsToDelete);

        // friendRequestDtos에 돌면서 친구 목록에 없으면 새로운 친구로 추가
        for (FriendRequestDto friendRequestDto : friendRequestDtos) {
            String phoneNumber = friendRequestDto.getPhoneNumber();
            if (!friendsMap.containsKey(phoneNumber)) {
                // todo : 각 friend profileimage 추가
                Friend newFriend = new Friend(friendRequestDto, null, member);
                friendRepository.save(newFriend);
            }
        }
    }

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
    public Member isLogin(UserDetailsImpl userDetails){
        if(userDetails != null){
            return userDetails.getUser();
        } else{
            throw new GlobalException(GlobalErrorCode.LOGIN_REQUIRED);
        }
    }

    @Transactional
    public void checkRole(Long friendId, Member member){
        friendRepository.findByFriendIdAndMember(friendId, member).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.FRIEND_NOT_FOUND)
        );
    }
}
