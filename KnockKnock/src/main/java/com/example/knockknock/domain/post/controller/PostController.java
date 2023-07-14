package com.example.knockknock.domain.post.controller;

import com.example.knockknock.domain.comment.dto.CommentRegisterRequestDto;
import com.example.knockknock.domain.comment.dto.CommentRegisterResponseDto;
import com.example.knockknock.domain.comment.dto.CommentUpdateRequestDto;
import com.example.knockknock.domain.comment.dto.GetCommentsResponseDto;
import com.example.knockknock.domain.post.dto.*;
import com.example.knockknock.domain.post.service.CommentService;
import com.example.knockknock.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getAll/{boardId}")
    public ResponseEntity<List<GetPostsResponseDto>> getPosts(
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(postService.getPostsByBoard(id), HttpStatus.OK);
    }

    @GetMapping("/ageGroup")
    public ResponseEntity<List<GetPostsByAgeResponseDto>> getPostsByAge(
            @RequestBody GetPostsByAgeRequestDto request
    ) {
        return new ResponseEntity<>(postService.getPostsByAgeGroup(request), HttpStatus.OK);
    }


    @GetMapping("/get/{postId}")
    public ResponseEntity getPostDetail(
            @PathVariable("postId") Long id
    ) {
        return new ResponseEntity<>(postService.getPostDetail(id), HttpStatus.OK);
    }

    @PutMapping("/edit/{postId}")
    public ResponseEntity updatePost(
            @PathVariable("postId") Long id,
            @RequestBody @Valid PostUpdateRequestDto request
    ) {
        postService.updatePost(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity deletePost(
            @PathVariable("postId") Long id
    ) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{userId}/like/{postId}")
    public ResponseEntity likePost(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        postService.likePost(userId, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/dislike/{postId}")
    public ResponseEntity deletePostLike(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        postService.deletePostLike(userId, postId);
        return ResponseEntity.ok().build();
    }


}
