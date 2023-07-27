package com.example.knockknock.domain.friend.controller;

import com.example.knockknock.domain.friend.dto.requestDto.FriendRequestDto;
import com.example.knockknock.domain.friend.service.FriendService;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "friend", description = "friend API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;

    // 친구 연락처 추가
    @Operation(summary = "친구 연락처 추가", description = "친구 연락처 추가")
    @PostMapping()
    public ResponseEntity addFriends(@RequestBody FriendRequestDto friendRequestDto){
        friendService.addFriends(friendRequestDto);
        return ResponseMessage.SuccessResponse("친구 추가 성공", "");
    }

    // 유저의 전체 친구 조회 /friends
    @Operation(summary = "친구 전체 조회", description = "친구 전체 조회")
    @GetMapping()
    public ResponseEntity getFriends(){
        return ResponseMessage.SuccessResponse("조회 성공", friendService.getFriends());
    }

    // 친구 검색 /friends/search

    // 친구 페이지 내용 불러오기 (첫 화면) GET: /friends/{friendId}
    @Operation(summary = "친구 상세 조회", description = "친구 상세 조회")
    @GetMapping("/{friendId}")
    public ResponseEntity getDetailFriend(@PathVariable Long friendId/*, @AuthenticationPrincipal MemberDetailsImpl memberDetails*/){
        return ResponseMessage.SuccessResponse("조회 성공", friendService.getDetailFriend(friendId));
    }

    // 친구 번호, 사진 편집
    @Operation(summary = "친구 정보 편집", description = "친구 정보 편집")
    @PatchMapping("/{friendId}/edit")
    public ResponseEntity updateFriendInfo(@PathVariable Long friendId, @RequestBody FriendRequestDto friendRequestDto/*, @AuthenticationPrincipal MemberDetailsImpl memberDetails*/){
        friendService.updateFriendInfo(friendId, friendRequestDto/*, memberDetails*/);
        return ResponseMessage.SuccessResponse("친구 정보 수정 성공", "");
    }

    // 친구 삭제
    @Operation(summary = "친구 삭제", description = "친구 삭제")
    @DeleteMapping("/{friendId}")
    public ResponseEntity deleteFriend(@PathVariable Long friendId/*, @AuthenticationPrincipal MemberDetailsImpl memberDetails*/){
        friendService.deleteFriend(friendId/*, memberDetails*/);
        return ResponseMessage.SuccessResponse("친구 삭제 성공", "");
    }

    // 찐친 등록하기
    @Operation(summary = "찐친 등록하기", description = "찐친 등록하기")
    @PostMapping("/{friendId}/bestFriend")
    public ResponseEntity updateBestFriendStatus(@PathVariable Long friendId){
        friendService.updateBestFriendStatus(friendId);
        return ResponseMessage.SuccessResponse("수정 성공", "");
    }
}
