package com.example.knockknock.domain.comment.controller;

import com.example.knockknock.domain.comment.dto.CommentRegisterRequestDto;
import com.example.knockknock.domain.comment.dto.CommentRegisterResponseDto;
import com.example.knockknock.domain.comment.dto.CommentUpdateRequestDto;
import com.example.knockknock.domain.comment.dto.GetCommentsResponseDto;
import com.example.knockknock.domain.post.service.CommentService;
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
public class ComentController {
    private final CommentService commentService;
    @PostMapping("/{postId}")
    public ResponseEntity<CommentRegisterResponseDto> registerComment(
            @RequestBody @Valid CommentRegisterRequestDto request,
            @PathVariable("postId") Long id
    ) {
        return new ResponseEntity<>(commentService.registerComment(id, request), HttpStatus.OK);
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<GetCommentsResponseDto>> getComments(
            @PathVariable("postId") Long id
    ) {
        return new ResponseEntity<>(commentService.getComments(id), HttpStatus.OK);
    }

    @PutMapping("/edit/{commentId}")
    public ResponseEntity updateComment(
            @RequestBody @Valid CommentUpdateRequestDto request,
            @PathVariable("commentId") Long id
    ) {
        commentService.updateComment(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity deleteComment(
            @PathVariable("commentId") Long id
    ) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
