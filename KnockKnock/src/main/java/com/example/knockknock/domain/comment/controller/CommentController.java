package com.example.knockknock.domain.comment.controller;

import com.example.knockknock.domain.comment.dto.CommentRegisterRequestDto;
import com.example.knockknock.domain.comment.dto.CommentRegisterResponseDto;
import com.example.knockknock.domain.comment.dto.CommentUpdateRequestDto;
import com.example.knockknock.domain.comment.dto.GetCommentsResponseDto;
import com.example.knockknock.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/post/comment")
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{postId}")
    public ResponseEntity<CommentRegisterResponseDto> registerComment(
            @RequestBody @Valid CommentRegisterRequestDto request,
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(commentService.registerComment(postId, request), HttpStatus.OK);
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<GetCommentsResponseDto>> getComments(
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(commentService.getComments(postId), HttpStatus.OK);
    }

    @PutMapping("/edit/{commentId}")
    public ResponseEntity updateComment(
            @RequestBody @Valid CommentUpdateRequestDto request,
            @PathVariable Long commentId
    ) {
        commentService.updateComment(commentId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity deleteComment(
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
