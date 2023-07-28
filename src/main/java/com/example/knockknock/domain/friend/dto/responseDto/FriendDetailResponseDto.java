package com.example.knockknock.domain.friend.dto.responseDto;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.notification.entity.Notification;
import com.example.knockknock.domain.notification.entity.NotificationRepeatEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FriendDetailResponseDto {
    private String friendName;
    private String profileImageURL;
    private String phoneNumber;

    // 찐친 여부
    private boolean isBestFriend;

    // 예정 알림
    @DateTimeFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime notificationDate;

    // 반복주기
    private NotificationRepeatEnum notificationRepeat;

    public FriendDetailResponseDto(Friend friend, Notification notification) {
        this.friendName = friend.getFriendName();
        this.profileImageURL = friend.getProfileImageURL();
        this.phoneNumber = friend.getPhoneNumber();
        this.isBestFriend = friend.isBestFriend();
        if (notification != null) {
            this.notificationDate = notification.getNotificationDate();
            this.notificationRepeat = notification.getNotificationRepeat();
        } else {
            this.notificationDate = null;
            this.notificationRepeat = null;
        }
    }
}
