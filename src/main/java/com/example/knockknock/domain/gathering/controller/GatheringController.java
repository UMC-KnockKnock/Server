package com.example.knockknock.domain.gathering.controller;

import com.example.knockknock.domain.gathering.dto.request.GatheringRequestDto;
import com.example.knockknock.domain.gathering.dto.request.GatheringUpdateRequestDto;
import com.example.knockknock.domain.gathering.service.GatheringService;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gathering")
public class GatheringController {
    private final GatheringService gatheringService;

    @PostMapping("/create")
    public ResponseEntity createGathering(@RequestBody GatheringRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        gatheringService.createGathering(request, userDetails);
        return ResponseMessage.SuccessResponse("모임 생성 성공", "");
    }

    @GetMapping
    public ResponseEntity getGatherings(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseMessage.SuccessResponse("불러오기 성공", gatheringService.getGatherings(userDetails));
    }

    @GetMapping("/{gatheringId}")
    public ResponseEntity getGatheringDetail(@PathVariable Long gatheringId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseMessage.SuccessResponse("조회 성공", gatheringService.getGatheringDetail(gatheringId, userDetails));
    }

    @PutMapping("/{gatheringId}/edit")
    public ResponseEntity updateGatheringInfo(@PathVariable Long gatheringId, @RequestBody GatheringUpdateRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        gatheringService.updateGatheringInfo(gatheringId, request, userDetails);
        return ResponseMessage.SuccessResponse("수정 완료", "");
    }

    @DeleteMapping("/{gatheringId/delete")
    public ResponseEntity deleteGathering(@PathVariable Long gatheringId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        gatheringService.deleteGathering(gatheringId, userDetails);
        return ResponseMessage.SuccessResponse("삭제 완료", "");
    }
}
