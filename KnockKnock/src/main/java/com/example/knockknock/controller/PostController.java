package com.example.knockknock.controller;

import com.example.knockknock.dto.*;
import com.example.knockknock.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
    public ResponseEntity<List<GetPostListResponseDto>> getPostList(
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(postService.getPostsByBoard(id), HttpStatus.OK);
    }

    @GetMapping("/ageGroup")
    public ResponseEntity<List<GetPostListByAgeResponseDto>> getPostByAge(
            @RequestBody GetPostListByAgeRequestDto request
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

    @DeleteMapping("/{userId}/like/{postId}")
    public ResponseEntity deletePostLike(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        postService.deletePostLike(userId, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/likeCount")
    public ResponseEntity<GetLikeCountResponseDto> getLikeCount(
            @PathVariable("postId") Long id
    ) {
        return new ResponseEntity<>(postService.getLikeCount(id), HttpStatus.OK);
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<CommentRegisterResponseDto> registerComment(
            @RequestBody @Valid CommentRegisterRequestDto request,
            @PathVariable("postId") Long id
    ) {
        return new ResponseEntity<>(postService.registerComment(id, request), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<GetCommentListResponseDto>> getCommentList(
            @PathVariable("postId") Long id
    ) {
        return new ResponseEntity<>(postService.getComments(id), HttpStatus.OK);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity updateComment(
            @RequestBody @Valid CommentUpdateRequestDto request,
            @PathVariable("commentId") Long id
    ) {
        postService.updateComment(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(
            @PathVariable("commentId") Long id
    ) {
        postService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
