package com.example.knockknock.domain.friend.dto.responseDto;

import com.example.knockknock.domain.friend.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendResponseDto {
    private Long friendId;
    private String friendName;
    private String profileImageURL;
    private String phoneNumber;
    private boolean bestFriend;

    public FriendResponseDto(Friend friend) {
        this.friendId = friend.getFriendId();
        this.friendName = friend.getFriendName();
        this.profileImageURL = friend.getProfileImageURL();
        this.phoneNumber = friend.getPhoneNumber();
        this.bestFriend = friend.isBestFriend();
    }

    public static FriendResponseDto of(Friend friend) {
        return FriendResponseDto.builder()
                .friendId(friend.getFriendId())
                .friendName(friend.getFriendName())
                .profileImageURL(friend.getProfileImageURL())
                .phoneNumber(friend.getPhoneNumber())
                .bestFriend(friend.isBestFriend())
                .build();
    }
}
