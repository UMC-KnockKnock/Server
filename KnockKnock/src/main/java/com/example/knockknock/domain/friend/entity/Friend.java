package com.example.knockknock.domain.friend.entity;

import com.example.knockknock.global.timestamp.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Friend extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @Column(nullable = true)
    private String friendName;

    @Column(nullable = false)
    private String profileImageURL;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean bestFriend;

//    @ManyToOne
//    @JoinColumn(name="MEMBER_ID", nullable = false)
//    private Member member;
}
