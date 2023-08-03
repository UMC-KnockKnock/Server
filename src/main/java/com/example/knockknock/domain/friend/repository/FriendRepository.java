package com.example.knockknock.domain.friend.repository;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByFriendIdAndMember(Long id, Member member);
    List<Friend> findAllByMember(Member member);

    List<Friend> findByMemberAndBestFriendIsTrue(Member member);
}
