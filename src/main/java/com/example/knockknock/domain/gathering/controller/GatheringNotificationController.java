package com.example.knockknock.domain.gathering.controller;

import com.example.knockknock.domain.gathering.dto.request.GatheringNotificationRequestDto;
import com.example.knockknock.domain.gathering.dto.request.GatheringNotificationUpdateRequestDto;
import com.example.knockknock.domain.gathering.entity.GatheringNotification;
import com.example.knockknock.domain.gathering.service.GatheringNotificationService;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "gathering notification", description = "gathering notification API")
@RequiredArgsConstructor
@RestController
public class GatheringNotificationController {
    private final GatheringNotificationService gatheringNotificationService;

    @PostMapping("/gathering/{gatheringId}/notification")
    public ResponseEntity createNotification(
            @PathVariable Long gatheringId,
            @RequestBody GatheringNotificationRequestDto request
    ) {
        gatheringNotificationService.createNotification(gatheringId, request);
        return ResponseMessage.SuccessResponse("알림이 생성되었습니다.", "");
    }

    @GetMapping("/gathering/{gatheringId}/notification")
    public ResponseEntity getNotification(
            @PathVariable Long gatheringId
    ) {

        return new ResponseEntity<>(gatheringNotificationService.getNotification(gatheringId), HttpStatus.OK);
    }

    @PutMapping("/gathering/{gatheringId}/notification")
    public ResponseEntity updateRepeat(
            @PathVariable Long gatheringId,
            @RequestBody GatheringNotificationUpdateRequestDto request
            ) {
        gatheringNotificationService.updateRepeat(gatheringId, request);
        return ResponseMessage.SuccessResponse("알림 주기가 수정되었습니다.", "");
    }

    @PostMapping("/gathering/{gatheringId}/notification/status")
    public ResponseEntity updateStatus(
            @PathVariable Long gatheringId
    ) {
        Boolean isActive = gatheringNotificationService.updateStatus(gatheringId);
        if(isActive){
            return ResponseMessage.SuccessResponse("모임에 대한 알림을 받습니다.", "");
        } else {
            return ResponseMessage.SuccessResponse("모임에 대한 알림을 받지 않습니다.", "");
        }
    }
}
