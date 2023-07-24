package com.example.knockknock.domain.notification.dto.requestDto;

import com.example.knockknock.domain.notification.entity.NotificationRepeatEnum;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NotificationRequestDto {
    private String notificationMemo;
    @DateTimeFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime notificationDate;
    private NotificationRepeatEnum notificationRepeat;
}
