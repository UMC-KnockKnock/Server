package com.example.knockknock.domain.gathering.dto.response;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.gathering.entity.Gathering;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GatheringDetailResponseDto {
    private String title;
    private List<GatheringMemberResponseDto> gatheringMembers;
    private String location;

    public static GatheringDetailResponseDto of(Gathering gathering){
        List<GatheringMemberResponseDto> members = gathering.getGatheringMembers().stream()
                .map(GatheringMemberResponseDto::from)
                .collect(Collectors.toList());
        return GatheringDetailResponseDto.builder()
                .title(gathering.getTitle())
                .gatheringMembers(members)
                .location(gathering.getLocation())
                .build();
    }
}
