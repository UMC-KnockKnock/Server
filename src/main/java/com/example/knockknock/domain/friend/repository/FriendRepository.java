package com.example.knockknock.domain.friend.repository;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByFriendIdAndMember(Long id, Member member);
    List<Friend> findAllByMember(Member member);

    List<Friend> findByMemberAndBestFriendIsTrue(Member member);

    @Query(value = "SELECT * FROM friend " +
            "WHERE (lower(friend_name) like lower(concat('%', :keyword, '%'))) " +
            "OR (lower(nickname) like lower(concat('%', :keyword, '%'))) " +
            "OR (lower(phone_number) like lower(concat('%', :keyword, '%'))) " +
            "ORDER BY created_at DESC ",
            nativeQuery = true)
    List<Friend> searchFriends(@Param("keyword") String keyword);
}
