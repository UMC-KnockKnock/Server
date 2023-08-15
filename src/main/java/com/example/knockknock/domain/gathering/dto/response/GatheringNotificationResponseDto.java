package com.example.knockknock.domain.gathering.dto.response;

import com.example.knockknock.domain.gathering.entity.GatheringNotification;
import com.example.knockknock.domain.gathering.entity.GatheringNotificationRepeat;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GatheringNotificationResponseDto {
    private Long gatheringId;
    private String title;
    private GatheringNotificationRepeat notificationRepeat;
    private boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public static GatheringNotificationResponseDto of(GatheringNotification notification){
        return GatheringNotificationResponseDto.builder()
                .gatheringId(notification.getGathering().getGatheringId())
                .title(notification.getTitle())
                .notificationRepeat(notification.getNotificationRepeat())
                .active(notification.isActive())
                .createdDate(notification.getCreatedDate())
                .build();
    }
}
