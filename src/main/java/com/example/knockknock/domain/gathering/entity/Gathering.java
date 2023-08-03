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


    public void updateGathering(GatheringUpdateRequestDto request, List<Friend> gatheringMembers) {
        if(request.getTitle() != null){
            this.title = request.getTitle();}

        if(request.getLocation() != null){
            this.location = request.getLocation();}

        if(request.getGatheringTime() != null){
            this.gatheringTime = request.getGatheringTime();}
        // 새로운 gatheringMembers 목록을 만듭니다.
        List<Friend> currentGatheringMembers = new ArrayList<>(this.gatheringMembers);

        // 복사한 리스트를 반복하면서 요청에 포함되지 않은 멤버를 제거합니다.
        Iterator<Friend> iterator = currentGatheringMembers.iterator();
        while (iterator.hasNext()) {
            Friend member = iterator.next();
            if (!request.getGatheringMemberIds().contains(member.getFriendId())) {
                iterator.remove();
            }
        }

        // gatheringMembers 리스트에 요청에서 추가된 새로운 멤버를 추가합니다.
        for (Friend member : gatheringMembers) {
            if (!currentGatheringMembers.contains(member) && request.getGatheringMemberIds().contains(member.getFriendId())) {
                currentGatheringMembers.add(member);
            }
        }

        // 수정된 리스트로 gatheringMembers 리스트를 업데이트합니다.
        this.gatheringMembers = currentGatheringMembers;

    }
}
