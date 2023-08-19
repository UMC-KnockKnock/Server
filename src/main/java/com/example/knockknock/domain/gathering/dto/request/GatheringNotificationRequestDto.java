package com.example.knockknock.domain.gathering.dto.request;

import com.example.knockknock.domain.gathering.entity.GatheringNotificationRepeat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GatheringNotificationRequestDto {
    @Builder.Default
    private boolean active = false;
    @Builder.Default
    private GatheringNotificationRepeat notificationRepeat = GatheringNotificationRepeat.EVERY_TWO_MONTHS;
}
