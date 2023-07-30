package com.example.knockknock.domain.friend.entity;

import com.example.knockknock.domain.friend.dto.requestDto.FriendRequestDto;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.global.timestamp.TimeStamped;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Slf4j
public class Friend extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @Column(nullable = false)
    private String friendName;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = false)
    private String profileImageURL;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean bestFriend;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID", nullable = false)
    private Member member;

    public void setBestFriend(){
        this.bestFriend = !bestFriend;
    }

    public Friend(FriendRequestDto friendRequestDto, String profileImageURL, Member member){
        this.profileImageURL = Objects.requireNonNullElse(profileImageURL, "https://e7.pngegg.com/pngimages/195/830/png-clipart-emoji-silhouette-service-company-person-emoji-cdr-head.png");
        this.friendName = friendRequestDto.getFriendName();
        this.nickname = friendRequestDto.getFriendName();
        this.phoneNumber = friendRequestDto.getPhoneNumber();
        this.bestFriend = false;
        this.member = member;
    }

    public void update(FriendRequestDto friendRequestDto, String profileImageURL){
        if (profileImageURL != null) this.profileImageURL = profileImageURL;
        this.friendName = friendRequestDto.getFriendName();
        this.nickname = friendRequestDto.getNickName();
        this.phoneNumber = friendRequestDto.getPhoneNumber();
    }
}
