package com.example.knockknock.domain.gathering.dto.request;

import com.example.knockknock.domain.friend.entity.Friend;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GatheringUpdateRequestDto {
    private String title;
    private List<Friend> gatheringMembers;

    private String location;

    private LocalDateTime gatheringTime;

}
