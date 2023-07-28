package com.example.knockknock.domain.notification.entity;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.notification.dto.requestDto.NotificationRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
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

    @Column(nullable = true)
    private LocalDateTime notificationDate;

    @Column(nullable = true)
    @Enumerated(value = EnumType.STRING)
    private NotificationRepeatEnum notificationRepeat;

    public Notification(Friend friend, NotificationRequestDto notificationRequestDto) {
        this.friend = friend;
        this.notificationMemo = notificationRequestDto.getNotificationMemo();
        this.notificationDate = notificationRequestDto.getNotificationDate();
        this.notificationRepeat = notificationRequestDto.getNotificationRepeat();
    }
}
