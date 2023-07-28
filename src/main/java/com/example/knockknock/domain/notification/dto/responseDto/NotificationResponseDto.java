package com.example.knockknock.domain.notification.dto.responseDto;

import com.example.knockknock.domain.notification.entity.Notification;
import com.example.knockknock.domain.notification.entity.NotificationRepeatEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationResponseDto {
    private String notificationMemo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime notificationDate;

    private NotificationRepeatEnum notificationRepeat;

    public NotificationResponseDto(Notification notification) {
        this.notificationMemo = (notification != null) ? notification.getNotificationMemo() : null;
        this.notificationDate = (notification != null) ? notification.getNotificationDate() : null;
        this.notificationRepeat = (notification != null) ? notification.getNotificationRepeat() : null;
    }
}
