package com.example.knockknock.domain.postlike.controller;

import com.example.knockknock.domain.postlike.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/postLike")
@RequiredArgsConstructor
@RestController
public class PostLikeController {
    private final PostLikeService postLikeService;
    @PostMapping("/{userId}/like/{postId}")
    public ResponseEntity likePost(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        postLikeService.likePost(userId, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/dislike/{postId}")
    public ResponseEntity deletePostLike(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        postLikeService.deletePostLike(userId, postId);
        return ResponseEntity.ok().build();
    }
}
