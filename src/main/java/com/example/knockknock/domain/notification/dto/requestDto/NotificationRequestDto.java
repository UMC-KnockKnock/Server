package com.example.knockknock.domain.notification.dto.requestDto;

import com.example.knockknock.domain.notification.entity.NotificationRepeatEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationRequestDto {
    private String notificationMemo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime notificationDate;
    private NotificationRepeatEnum notificationRepeat;
}
