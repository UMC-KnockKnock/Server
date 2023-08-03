package com.example.knockknock.domain.bestfriend.service;

import com.example.knockknock.domain.bestfriend.dto.BestFriendResponseDto;
import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.friend.repository.FriendRepository;
import com.example.knockknock.domain.friend.service.GetFriendService;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.member.service.MemberIsLoginService;
import com.example.knockknock.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BestFriendService {
    private final FriendRepository friendRepository;
    private final GetFriendService getFriendService;
    private final NotificationService notificationService;
    private final MemberIsLoginService memberIsLoginService;

    public List<BestFriendResponseDto> getBestFriends(UserDetailsImpl userDetails){
        Member member = memberIsLoginService.isLogin(userDetails);
        List<Friend> bestFriends = friendRepository.findByMemberAndBestFriendIsTrue(member);
        return bestFriends.stream()
                .map(BestFriendResponseDto::from)
                .collect(Collectors.toList());
    }

    public void addToBestFriend(List<Long> friendIds, UserDetailsImpl userDetails) {
        Member member = memberIsLoginService.isLogin(userDetails);

        // 현재 로그인한 멤버의 친구 목록 가져오기
        List<Friend> friends = friendRepository.findAllByMember(member);

        // ID가 일치하는 친구들을 찾아서 bestFriend 필드를 업데이트
        for (Friend friend : friends) {
            if (friendIds.contains(friend.getFriendId())) {
                friend.setBestFriend(true);
            }
        }

        // 업데이트된 친구들을 데이터베이스에 저장
        friendRepository.saveAll(friends);
    }
}
