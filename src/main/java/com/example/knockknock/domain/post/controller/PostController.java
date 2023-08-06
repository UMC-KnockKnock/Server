package com.example.knockknock.domain.post.controller;

import com.example.knockknock.domain.hashtag.dto.HashtagRegisterRequestDto;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.post.dto.request.*;
import com.example.knockknock.domain.post.service.PostService;
import com.example.knockknock.global.message.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;


    @PostMapping("/create")
    public ResponseEntity createPost(
            @RequestPart("request")
            @Valid
            PostCreateRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(required = false) List<MultipartFile> images)
    {
        postService.createPost(request, images, userDetails);
        return ResponseMessage.SuccessResponse("게시글 작성 완료", "");
    }

    @GetMapping("/{postId}")
    public ResponseEntity getPostDetail(
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(postService.getPostDetail(postId), HttpStatus.OK);
    }

    @GetMapping("/myPosts")
    public ResponseEntity getMyPosts(
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(postService.getMyPosts(userDetails), HttpStatus.OK);
    }
    @PutMapping("/{postId}")
    public ResponseEntity updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(required = false) List<MultipartFile> images
    ) {
        postService.updatePost(postId, request, images, userDetails);
        return ResponseMessage.SuccessResponse("게시글 수정 완료", "");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        postService.deletePost(postId, userDetails);
        return ResponseMessage.SuccessResponse("게시글 삭제 완료", "");
    }


    @GetMapping("/{postId}/share")
    public ResponseEntity<String> sharePost(
            @PathVariable Long postId, HttpServletRequest request
    ) {
        return new ResponseEntity<>(postService.sharePost(postId, request), HttpStatus.OK);
    }
}
