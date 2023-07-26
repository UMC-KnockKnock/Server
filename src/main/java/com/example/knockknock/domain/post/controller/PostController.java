package com.example.knockknock.domain.post.controller;

import com.example.knockknock.domain.hashtag.dto.HashtagRegisterRequestDto;
import com.example.knockknock.domain.post.dto.request.*;
import com.example.knockknock.domain.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;


    @PostMapping("/create")
    public ResponseEntity createPost(
            @RequestBody
            @Valid
            PostCreateRequestDto request) {
        postService.createPost(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity getPostDetail(
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(postService.getPostDetail(postId), HttpStatus.OK);
    }

    @PutMapping("/edit/{postId}")
    public ResponseEntity updatePost(
            @PathVariable Long postId,
            @RequestBody @Valid PostUpdateRequestDto request
    ) {
        postService.updatePost(postId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("/share/{postId}")
    public ResponseEntity<String> sharePost(
            @PathVariable Long postId, HttpServletRequest request
    ) {
        return new ResponseEntity<>(postService.sharePost(postId, request), HttpStatus.OK);
    }
}