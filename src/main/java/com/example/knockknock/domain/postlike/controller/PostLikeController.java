package com.example.knockknock.domain.postlike.controller;

import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.postlike.service.PostLikeService;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostLikeController {
    private final PostLikeService postLikeService;

    @Operation(summary = "게시글 좋아요 버튼 (토큰O)", description = "좋아요 누르기 - 이미 좋아요 한거 또 누르면 좋아요 취소")
    @PostMapping("/post/{postId}/like")
    public ResponseEntity likePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            )
    {
        String message = postLikeService.likePost(postId, userDetails);
        return ResponseMessage.SuccessResponse(message, "");
    }

    @Operation(summary = "특정 게시글에 눌린 좋아요 정보 (토큰X)", description = "게시글id, 좋아요한 멤버의 id + 닉네임, 좋아요 누른 시간 출력")
    @GetMapping("/post/{postId}/likes")
    public ResponseEntity getPostLikes(
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(postLikeService.getPostLikes(postId), HttpStatus.OK);
    }

}
