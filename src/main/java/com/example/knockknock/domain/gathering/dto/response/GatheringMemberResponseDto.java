package com.example.knockknock.domain.gathering.dto.response;

import com.example.knockknock.domain.friend.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GatheringMemberResponseDto {

    private String friendName;
    private String phoneNumber;
    private boolean bestFriend;

    public static GatheringMemberResponseDto from(Friend friend) {
        return GatheringMemberResponseDto.builder()
                .friendName(friend.getFriendName())
                .phoneNumber(friend.getPhoneNumber())
                .bestFriend(friend.isBestFriend())
                .build();
    }
}
