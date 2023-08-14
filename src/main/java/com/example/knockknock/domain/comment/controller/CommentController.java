package com.example.knockknock.domain.comment.controller;

import com.example.knockknock.domain.comment.dto.request.CommentRegisterRequestDto;
import com.example.knockknock.domain.comment.dto.request.CommentUpdateRequestDto;
import com.example.knockknock.domain.comment.dto.response.GetCommentsResponseDto;
import com.example.knockknock.domain.comment.service.CommentService;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "특정 게시글에 댓글 달기 (토큰O)", description = "특정 게시글에 댓글 달기")
    @PostMapping("/post/{postId}/comment/register")
    public ResponseEntity registerComment(
            @RequestBody @Valid CommentRegisterRequestDto request,
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.registerComment(postId, request, userDetails);
        return ResponseMessage.SuccessResponse("댓글 작성 완료", "");
    }

    @Operation(summary = "특정 게시글에 달린 댓글들 (토큰X)", description = "특정 게시글에 달린 모든 댓글 불러오기")
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<GetCommentsResponseDto>> getComments(
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(commentService.getComments(postId), HttpStatus.OK);
    }

    @Operation(summary = "작성자와 비교 동작 (토큰O)", description = "현재 접근한 댓글이 내가 작성한 것인지 확인")
    @PostMapping("/{commentId}/verification")
    public ResponseEntity isMyComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Boolean isVerified = commentService.isMyComment(commentId, userDetails);
        if(isVerified){
            return ResponseMessage.SuccessResponse("작성자와 일치합니다.", "");
        } else return ResponseMessage.ErrorResponse(GlobalErrorCode.PERMISSION_DENIED);
    }

    @Operation(summary = "댓글 수정하기 (토큰X)", description = "내 댓글 수정하기")
    @PutMapping("/comment/{commentId}")
    public ResponseEntity updateComment(
            @RequestBody @Valid CommentUpdateRequestDto request,
            @PathVariable Long commentId
    ) {

        commentService.updateComment(commentId, request);
        return ResponseMessage.SuccessResponse("댓글 수정 완료", "");
    }

    @Operation(summary = "댓글 삭제하기 (토큰X)", description = "내 댓글 삭제하기")
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ResponseMessage.SuccessResponse("댓글 삭제 완료", "");
    }


}
