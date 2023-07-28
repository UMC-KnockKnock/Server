package com.example.knockknock.domain.notification.controller;

import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.notification.dto.requestDto.NotificationRequestDto;
import com.example.knockknock.domain.notification.service.NotificationService;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "notification", description = "notification API")
@RequiredArgsConstructor
@RestController
public class NotificationController {
    private final NotificationService notificationService;
    //  연락주기 및 예정 알림 불러오기 GET : /friends/{friendId}/schedule
    @Operation(summary = "연락주기 및 예정알림", description = "연락주기 및 예정 알림 보기")
    @GetMapping("/friends/{friendId}/schedules")
    public ResponseEntity getSchedule(@PathVariable Long friendId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseMessage.SuccessResponse("조회 성공", notificationService.getSchedule(friendId, userDetails));
    }

    //  연락주기 및 예정 알림 저장하기 POST : /friends/{friendId}/schedule
    @Operation(summary = "연락주기 및 예정알림", description = "연락주기 및 예정 알림 저장")
    @PostMapping("/friends/{friendId}/schedules")
    public ResponseEntity createSchedule(@PathVariable Long friendId, @RequestBody NotificationRequestDto notificationRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        notificationService.createSchedule(friendId, notificationRequestDto, userDetails);
        return ResponseMessage.SuccessResponse("게시 성공", "");
    }

    // 연락주기 및 예정 알림 수정하기 PATCH : /friends/{friendId}/schedule
    @Operation(summary = "연락주기 및 예정알림", description = "연락주기 및 예정 알림 수정")
    @PatchMapping("/schedules/{notificationId}")
    public ResponseEntity updateSchedule(@PathVariable Long notificationId, @RequestBody NotificationRequestDto notificationRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        notificationService.updateSchedule(notificationId, notificationRequestDto, userDetails);
        return ResponseMessage.SuccessResponse("수정 성공", "");
    }

    // 연락주기 및 예정 알림 삭제하기 DELETE : /friends/{friendId}/schedule
    // xxx : softdelete로 구현 할 것
    @Operation(summary = "연락주기 및 예정알림", description = "연락주기 및 예정 알림 삭제")
    @DeleteMapping("schedules/{notificationId}")
    public ResponseEntity deleteSchedule(@PathVariable Long notificationId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        notificationService.deleteSchedule(notificationId, userDetails);
        return ResponseMessage.SuccessResponse("삭제 성공", "");
    }

}
