package com.example.knockknock.domain.gathering.dto.request;

import com.example.knockknock.domain.gathering.entity.GatheringNotificationRepeat;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GatheringNotificationUpdateRequestDto {
    private GatheringNotificationRepeat notificationRepeat;
}
