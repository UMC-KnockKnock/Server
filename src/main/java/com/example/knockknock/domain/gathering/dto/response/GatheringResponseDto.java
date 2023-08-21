package com.example.knockknock.domain.gathering.dto.response;

import com.example.knockknock.domain.gathering.entity.Gathering;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GatheringResponseDto {
    private Long gatheringId;
    private String title;

    public static GatheringResponseDto of(Gathering gathering){
        return GatheringResponseDto.builder()
                .gatheringId(gathering.getGatheringId())
                .title(gathering.getTitle())
                .build();
    }
}
