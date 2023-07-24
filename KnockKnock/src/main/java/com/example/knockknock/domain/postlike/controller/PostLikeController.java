package com.example.knockknock.domain.postlike.controller;

import com.example.knockknock.domain.postlike.dto.PostLikeRequestDto;
import com.example.knockknock.domain.postlike.service.PostLikeService;
import com.example.knockknock.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/post/like")
@RequiredArgsConstructor
@RestController
public class PostLikeController {
    private final PostLikeService postLikeService;
    @PostMapping
    public ResponseEntity likePost(
            @RequestBody PostLikeRequestDto request
            ) {
        String message = postLikeService.likePost(request);
        return ResponseMessage.SuccessResponse(message, "");
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity getPostLikes(
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(postLikeService.getPostLikes(postId), HttpStatus.OK);
    }

}
