package com.example.knockknock.domain.notification.controller;

import com.example.knockknock.domain.notification.dto.requestDto.NotificationRequestDto;
import com.example.knockknock.domain.notification.service.NotificationService;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "notification", description = "notification API")
@RequiredArgsConstructor
@RestController
public class NotificationController {
    private final NotificationService notificationService;
    //- [ ]  연락주기 및 예정 알림 불러오기 GET : /friends/{friendId}/schedule
    @Operation(summary = "연락주기 및 예정알림", description = "연락주기 및 예정 알림 저장")
    @PostMapping("/friends/{friendId}/schedules")
    public ResponseEntity getSchedule(@PathVariable Long friendId/*, @AuthenticationPrincipal MemberDetailsImpl memberDetails*/){
        return ResponseMessage.SuccessResponse("조회 성공", notificationService.getSchedule(friendId));
    }

    //- [ ]  연락주기 및 예정 알림 저장하기 POST : /friends/{friendId}/schedule
    @Operation(summary = "연락주기 및 예정알림", description = "연락주기 및 예정 알림 저장")
    @PostMapping("/friends/{friendId}/schedules")
    public ResponseEntity createSchedule(@PathVariable Long friendId, NotificationRequestDto notificationRequestDto/*, @AuthenticationPrincipal MemberDetailsImpl memberDetails*/){
        notificationService.createSchedule(friendId, notificationRequestDto);
        return ResponseMessage.SuccessResponse("게시 성공", "");
    }

    //- [ ]  연락주기 및 예정 알림 수정하기 PATCH : /friends/{friendId}/schedule
    @Operation(summary = "연락주기 및 예정알림", description = "연락주기 및 예정 알림 수정")
    @PostMapping("/schedules/{notificationId}")
    public ResponseEntity updateSchedule(@PathVariable Long notificationId, NotificationRequestDto notificationRequestDto/*, @AuthenticationPrincipal MemberDetailsImpl memberDetails*/){
        notificationService.updateSchedule(notificationId, notificationRequestDto);
        return ResponseMessage.SuccessResponse("게시 성공", "");
    }

    //- [ ]  연락주기 및 예정 알림 삭제하기 DELETE : /friends/{friendId}/schedule
    // xxx : softdelete로 구현 할 것
    @Operation(summary = "연락주기 및 예정알림", description = "연락주기 및 예정 알림 수정")
    @PostMapping("schedules/{notificationId}")
    public ResponseEntity deleteSchedule(@PathVariable Long notificationId/*, @AuthenticationPrincipal MemberDetailsImpl memberDetails*/){
        notificationService.deleteSchedule(notificationId);
        return ResponseMessage.SuccessResponse("게시 성공", "");
    }

}
