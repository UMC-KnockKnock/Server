package com.example.knockknock.domain.gathering.entity;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.gathering.dto.request.GatheringUpdateRequestDto;
import com.example.knockknock.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Gathering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GATHERING_ID")
    private Long gatheringId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "TIME")
    private LocalDateTime gatheringTime;

    @ManyToMany
    @JoinTable(
            name = "gathering_members",
            joinColumns = @JoinColumn(name = "GATHERING_ID"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Friend> gatheringMembers = new ArrayList<>();

}
