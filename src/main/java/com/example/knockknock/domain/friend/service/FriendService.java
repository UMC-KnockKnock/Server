package com.example.knockknock.domain.friend.service;

import com.example.knockknock.domain.friend.dto.requestDto.FriendRequestDto;
import com.example.knockknock.domain.friend.dto.responseDto.FriendDetailResponseDto;
import com.example.knockknock.domain.friend.dto.responseDto.FriendResponseDto;
import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.friend.repository.FriendRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.member.service.MemberIsLoginService;
import com.example.knockknock.domain.notification.entity.Notification;
import com.example.knockknock.domain.notification.service.NotificationService;
import com.example.knockknock.global.image.service.S3Service;
import java.io.IOException;
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
    private final MemberIsLoginService memberIsLoginService;
    private final S3Service s3Service;

    @Transactional
    public void createFriends(FriendRequestDto friendRequestDto, UserDetailsImpl userDetails, MultipartFile profileImage) {
        // isLogin
        Member member = memberIsLoginService.isLogin(userDetails);
        // 해당 member 정보도 friend에 저장
        String imageUrl = null;
        if(profileImage != null && !profileImage.isEmpty()){
            try {
                imageUrl = s3Service.uploadImage(profileImage);
            } catch (IOException e) {
            throw new RuntimeException(e);
            }
        } else {imageUrl = "https://knockknockimage.s3.ap-northeast-2.amazonaws.com/%EA%B8%B0%EB%B3%B8+%EC%9D%B4%EB%AF%B8%EC%A7%80.png";}
        Friend friend = new Friend(friendRequestDto, imageUrl, member);
        friendRepository.save(friend);
    }

    public void contactFriend(List<FriendRequestDto> friendRequestDtos, UserDetailsImpl userDetails) {
        // isLogin
        Member member = memberIsLoginService.isLogin(userDetails);

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
                String imageUrl = "https://knockknockimage.s3.ap-northeast-2.amazonaws.com/%EA%B8%B0%EB%B3%B8+%EC%9D%B4%EB%AF%B8%EC%A7%80.png";
                Friend newFriend = new Friend(friendRequestDto, imageUrl, member);
                friendRepository.save(newFriend);
            }
        }
    }

    public List<FriendResponseDto> getFriends(UserDetailsImpl userDetails) {
        Member member = memberIsLoginService.isLogin(userDetails);
        List<FriendResponseDto> friendResponseDtos = new ArrayList<>();
        // todo : memberId로 friend 찾아오기
        List<Friend> friends = friendRepository.findAllByMember(member);
        for (Friend friend : friends) {
            FriendResponseDto friendResponseDto = new FriendResponseDto(friend);
            friendResponseDtos.add(friendResponseDto);
        }
        return friendResponseDtos;
    }

    @Transactional(readOnly = true)
    public FriendDetailResponseDto getDetailFriend(Long friendId, UserDetailsImpl userDetails) {
        // islogin
        Member member = memberIsLoginService.isLogin(userDetails);
        // Friend friend = getFriendService.getFriend(friendId);
        Friend friend = getFriendService.checkRole(friendId, member);
        // 연락 일정 정보
        Notification notification = notificationService.getNotificationSchedule(friendId, member);
        return new FriendDetailResponseDto(friend, notification);
    }

    @Transactional
    public void updateFriendInfo(Long friendId, FriendRequestDto friendRequestDto, UserDetailsImpl userDetails, MultipartFile profileImage) {
        // isLogin
        Member member = memberIsLoginService.isLogin(userDetails);
        // checkRole
        Friend friend = getFriendService.checkRole(friendId, member);
        // setter 안쓰는 방법 생각해보기
        if (friendRequestDto.getFriendName() != null) friend.setFriendName(friendRequestDto.getFriendName());
        if (friendRequestDto.getNickName() != null) friend.setNickname(friendRequestDto.getNickName());
        if (friendRequestDto.getPhoneNumber() != null) friend.setPhoneNumber(friendRequestDto.getPhoneNumber());

        // 원래 있던 프로필이미지 지우고 올리기
        if(profileImage != null) {
            String imageUrl = null;
            try {
                imageUrl = s3Service.uploadImage(profileImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } friend.setProfileImageURL(imageUrl);
        }

        // profileImageURL 구현
    }

    @Transactional
    public void deleteFriend(Long friendId, UserDetailsImpl userDetails) {
        // isLogin
        Member member = memberIsLoginService.isLogin(userDetails);
        // checkRole
        getFriendService.checkRole(friendId, member);
        // 연관관계 정리
        friendRepository.deleteById(friendId);
    }

    // 찐친 등록
    @Transactional
    public void updateBestFriendStatus(Long friendId, UserDetailsImpl userDetails) {
        // isLogin
        Member member = memberIsLoginService.isLogin(userDetails);
        // checkRole
        Friend friend = getFriendService.checkRole(friendId, member);
        friend.setBestFriend();
    }

    @Transactional
    public List<FriendResponseDto> searchFriends(String keyword) {
        List<Friend> friends = friendRepository.searchFriends(keyword);

        return friends.stream()
                .map(FriendResponseDto::of)
                .collect(Collectors.toList());
    }
}
