package com.example.knockknock.domain.bestfriend.dto;

import com.example.knockknock.domain.friend.entity.Friend;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BestFriendResponseDto {
    private String friendName;
    private String phoneNumber;

    public static BestFriendResponseDto from(Friend friend){
        return BestFriendResponseDto.builder()
                .friendName(friend.getFriendName())
                .phoneNumber(friend.getPhoneNumber())
                .build();
    }
}
