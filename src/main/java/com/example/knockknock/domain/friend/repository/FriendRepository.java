package com.example.knockknock.domain.friend.repository;

import com.example.knockknock.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
