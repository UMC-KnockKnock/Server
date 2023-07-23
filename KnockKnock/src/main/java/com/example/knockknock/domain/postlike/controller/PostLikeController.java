package com.example.knockknock.domain.postlike.controller;

import com.example.knockknock.domain.postlike.dto.LikeRequestDto;
import com.example.knockknock.domain.postlike.service.PostLikeService;
import com.example.knockknock.global.message.ResponseMessage;
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
    @PostMapping("/like")
    public ResponseEntity likePost(
            @RequestBody LikeRequestDto request
            ) {
        String message = postLikeService.likePost(request);
        return ResponseMessage.SuccessResponse(message, "");
    }

}
