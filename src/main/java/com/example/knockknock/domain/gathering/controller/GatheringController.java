package com.example.knockknock.domain.gathering.controller;

import com.example.knockknock.domain.gathering.dto.request.GatheringRequestDto;
import com.example.knockknock.domain.gathering.dto.request.GatheringUpdateRequestDto;
import com.example.knockknock.domain.gathering.dto.request.LocationRecommendRequestDto;
import com.example.knockknock.domain.gathering.dto.response.GatheringDetailResponseDto;
import com.example.knockknock.domain.gathering.service.GatheringService;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gathering")
public class GatheringController {
    private final GatheringService gatheringService;

    @Operation(summary = "찐친관리 - 내 모임 - 모임 추가하기 (토큰O)", description = "모임 추가하기")
    @PostMapping("/create")
    public ResponseEntity createGathering(@RequestBody GatheringRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        gatheringService.createGathering(request, userDetails);
        return ResponseMessage.SuccessResponse("모임 생성 성공", "");
    }

    @Operation(summary = "내 모임 - 만남 장소 추천 (토큰X)", description = "장소 추천해드릴까요? 속 새로고침처럼 생긴 버튼 누를 때마다 서로 다른 카테고리의 장소들 5개 씩 추천")
    @PostMapping("/location")
    public ResponseEntity recommendLocation(
            @RequestBody LocationRecommendRequestDto request
    ) {
        return new ResponseEntity<>(gatheringService.recommendLocation(request), HttpStatus.OK);
    }

    @Operation(summary = "찐친관리 - 내 모임 목록 (토큰O)", description = "현재 로그인 한 회원이 만든 모임들 불러오기")
    @GetMapping
    public ResponseEntity getGatherings(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseMessage.SuccessResponse("불러오기 성공", gatheringService.getGatherings(userDetails));
    }

    @Operation(summary = "특정 모임의 상세정보 (토큰O)", description = "특정 모임을 눌렀을 때 상세정보 불러오기")
    @GetMapping("/{gatheringId}")
    public ResponseEntity getGatheringDetail(@PathVariable Long gatheringId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(gatheringService.getGatheringDetail(gatheringId, userDetails), HttpStatus.OK);
    }
    @Operation(summary = "모임 정보 수정하기 (토큰O)", description = "모임 상세정보 수정하기")
    @PutMapping("/{gatheringId}")
    public ResponseEntity updateGatheringInfo(@PathVariable Long gatheringId, @RequestBody GatheringUpdateRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        gatheringService.updateGatheringInfo(gatheringId, request, userDetails);
        return ResponseMessage.SuccessResponse("수정 완료", "");
    }

    @Operation(summary = "모임 삭제하기 (토큰O)", description = "모임 삭제하기")
    @DeleteMapping("/{gatheringId}")
    public ResponseEntity deleteGathering(@PathVariable Long gatheringId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        gatheringService.deleteGathering(gatheringId, userDetails);
        return ResponseMessage.SuccessResponse("삭제 완료", "");
    }
}
