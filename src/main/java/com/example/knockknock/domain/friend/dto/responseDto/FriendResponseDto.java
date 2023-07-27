package com.example.knockknock.domain.friend.dto.responseDto;

import com.example.knockknock.domain.friend.entity.Friend;
import lombok.Getter;

@Getter
public class FriendResponseDto {
    private String friendName;
    private String profileImageURL;
    private String phoneNumber;
    private boolean bestFriend;

    public FriendResponseDto(Friend friend) {
        this.friendName = friend.getFriendName();
        this.profileImageURL = friend.getProfileImageURL();
        this.phoneNumber = friend.getPhoneNumber();
        this.bestFriend = friend.isBestFriend();
    }
}
