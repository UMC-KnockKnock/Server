package com.example.knockknock.domain.gathering.dto.request;

import com.example.knockknock.domain.friend.entity.Friend;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GatheringRequestDto {
    private String title;
    private List<Long> gatheringMemberIds;

    private String location;
}
