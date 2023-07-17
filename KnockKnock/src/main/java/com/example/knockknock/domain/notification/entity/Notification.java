package com.example.knockknock.domain.notification.entity;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.global.timestamp.TimeStamped;
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
}
