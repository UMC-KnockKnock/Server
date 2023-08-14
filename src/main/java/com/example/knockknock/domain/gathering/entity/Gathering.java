package com.example.knockknock.domain.gathering.entity;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.gathering.dto.request.GatheringUpdateRequestDto;
import com.example.knockknock.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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

    private int queryIndex = 0;


    public void updateGathering(GatheringUpdateRequestDto request, List<Friend> newGatheringMembers) {
        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }

        if (request.getLocation() != null) {
            this.location = request.getLocation();
        }

        if (request.getGatheringTime() != null) {
            this.gatheringTime = request.getGatheringTime();
        }

        if (newGatheringMembers != null && !newGatheringMembers.isEmpty()) {
            List<Long> requestedMemberIds = request.getGatheringMemberIds();
            if(requestedMemberIds != null) {
                // 현재 gatheringMembers에서 요청에 포함되지 않은 멤버를 제거
                this.gatheringMembers.removeIf(member -> !requestedMemberIds.contains(member.getFriendId()));

                // 요청에 새로 포함된 멤버를 gatheringMembers에 추가
                for (Friend member : newGatheringMembers) {
                    if (!this.gatheringMembers.contains(member) && requestedMemberIds.contains(member.getFriendId())) {
                        this.gatheringMembers.add(member);
                    }
                }
            }
        }
    }

    public void setQueryIndex(int queryIndex) {
        this.queryIndex = queryIndex;
    }
}
