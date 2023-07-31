package com.example.knockknock.domain.comment.controller;

import com.example.knockknock.domain.comment.dto.*;
import com.example.knockknock.domain.comment.service.CommentService;
import com.example.knockknock.domain.comment.service.ReplyService;
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
    private final ReplyService replyService;
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

    @PostMapping("/reply/{commentId}")
    public ResponseEntity addReply(
            @PathVariable Long commentId,
            @RequestBody ReplyRegisterRequestDto request
            ) {
        replyService.addReply(commentId, request);
        return ResponseEntity.ok().build();
    }
}
