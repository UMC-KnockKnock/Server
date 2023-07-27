package com.example.knockknock.domain.notification.repository;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findAllByFriend(Friend friend);
}
