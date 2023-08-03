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
    private List<String> gatheringMembers;

    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gatheringTime;

    public static GatheringDetailResponseDto of(Gathering gathering){
        List<String> friendNames = gathering.getGatheringMembers().stream()
                .map(Friend::getFriendName)
                .collect(Collectors.toList());
        return GatheringDetailResponseDto.builder()
                .title(gathering.getTitle())
                .gatheringMembers(friendNames)
                .location(gathering.getLocation())
                .gatheringTime(gathering.getGatheringTime())
                .build();
    }
}
