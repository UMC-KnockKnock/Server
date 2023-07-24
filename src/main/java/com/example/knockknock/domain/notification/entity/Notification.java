package com.example.knockknock.domain.notification.entity;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.notification.dto.requestDto.NotificationRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Notification{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name="FRIEND_Id", nullable = false)
    private Friend friend;

    @Column(nullable = true)
    private String notificationMemo;

    @Column(nullable = false)
    private LocalDateTime notificationDate;

    @Column(nullable = false)
    private NotificationRepeatEnum notificationRepeat;

    public Notification(Friend friend, NotificationRequestDto notificationRequestDto) {
        this.friend = friend;
        this.notificationMemo = notificationRequestDto.getNotificationMemo();
        this.notificationDate = notificationRequestDto.getNotificationDate();
        this.notificationRepeat = notificationRequestDto.getNotificationRepeat();
    }

    public void update(NotificationRequestDto notificationRequestDto){
        // todo : patch니까 dto에 null값일 경우 처리 해주기
        this.notificationMemo = notificationRequestDto.getNotificationMemo();
        this.notificationDate = notificationRequestDto.getNotificationDate();
        this.notificationRepeat = notificationRequestDto.getNotificationRepeat();
    }
}
