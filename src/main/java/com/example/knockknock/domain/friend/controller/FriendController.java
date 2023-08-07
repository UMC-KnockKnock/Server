package com.example.knockknock.domain.friend.controller;

import com.example.knockknock.domain.friend.dto.requestDto.FriendRequestDto;
import com.example.knockknock.domain.friend.service.FriendService;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "friend", description = "friend API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;

    // 친구 연락처 추가 (수기로)
    @Operation(summary = "친구 연락처 추가", description = "친구 연락처 추가")
    @PostMapping("/create")
    public ResponseEntity createFriends(@RequestBody FriendRequestDto friendRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        friendService.createFriends(friendRequestDto, userDetails);
        return ResponseMessage.SuccessResponse("친구 추가 성공", "");
    }

    // 주소록 연동 친구 추가 및 삭제
    @Operation(summary = "연락처 연동", description = "연락처 연동 - 친구 연락처 추가 및 삭제")
    @PostMapping()
    public ResponseEntity contactFriend(@RequestBody List<FriendRequestDto> friendRequestDtos, @AuthenticationPrincipal UserDetailsImpl userDetails){
        friendService.contactFriend(friendRequestDtos, userDetails);
        return ResponseMessage.SuccessResponse("주소록 친구 추가 성공", "");
    }

    // 유저의 전체 친구 조회 /friends
    @Operation(summary = "친구 전체 조회", description = "친구 전체 조회")
    @GetMapping()
    public ResponseEntity getFriends(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseMessage.SuccessResponse("조회 성공", friendService.getFriends(userDetails));
    }

    // 친구 검색 /friends/search
    @GetMapping("/search")
    public ResponseEntity getFriendsByKeyword(@RequestParam("keyword") String keyword) {
        return ResponseMessage.SuccessResponse("친구 검색 성공", friendService.searchFriends(keyword));
    }

    // 친구 페이지 내용 불러오기 (첫 화면) GET: /friends/{friendId}
    @Operation(summary = "친구 상세 조회", description = "친구 상세 조회")
    @GetMapping("/{friendId}")
    public ResponseEntity getDetailFriend(@PathVariable Long friendId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseMessage.SuccessResponse("조회 성공", friendService.getDetailFriend(friendId, userDetails));
    }

    // 친구 번호, 사진 편집
    @Operation(summary = "친구 정보 편집", description = "친구 정보 편집")
    @PatchMapping("/{friendId}/edit")
    public ResponseEntity updateFriendInfo(@PathVariable Long friendId, @RequestBody FriendRequestDto friendRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        friendService.updateFriendInfo(friendId, friendRequestDto, userDetails);
        return ResponseMessage.SuccessResponse("친구 정보 수정 성공", "");
    }

    // 친구 삭제
    @Operation(summary = "친구 삭제", description = "친구 삭제")
    @DeleteMapping("/{friendId}")
    public ResponseEntity deleteFriend(@PathVariable Long friendId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        friendService.deleteFriend(friendId, userDetails);
        return ResponseMessage.SuccessResponse("친구 삭제 성공", "");
    }

    // 찐친 등록하기
    @Operation(summary = "찐친 등록하기", description = "찐친 등록하기")
    @PostMapping("/{friendId}/bestFriend")
    public ResponseEntity updateBestFriendStatus(@PathVariable Long friendId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        friendService.updateBestFriendStatus(friendId, userDetails);
        return ResponseMessage.SuccessResponse("수정 성공", "");
    }
}
