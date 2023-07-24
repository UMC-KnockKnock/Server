package com.example.knockknock.domain.notification.dto.responseDto;

import com.example.knockknock.domain.notification.entity.Notification;
import com.example.knockknock.domain.notification.entity.NotificationRepeatEnum;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Getter
public class NotificationResponseDto {
    private String notificationMemo;

    @DateTimeFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime notificationDate;
    private NotificationRepeatEnum notificationRepeat;

    public NotificationResponseDto(Notification notification) {
        this.notificationMemo = notification.getNotificationMemo();
        this.notificationDate = notification.getNotificationDate();
        this.notificationRepeat = notification.getNotificationRepeat();
    }
}
