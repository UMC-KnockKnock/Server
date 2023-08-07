package com.example.knockknock.domain.comment.controller;

import com.example.knockknock.domain.comment.dto.request.ReplyRegisterRequestDto;
import com.example.knockknock.domain.comment.dto.request.ReplyUpdateRequestDto;
import com.example.knockknock.domain.comment.service.ReplyService;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/comment")
@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/reply/{commentId}")
    public ResponseEntity addReply(
            @PathVariable Long commentId,
            @RequestBody ReplyRegisterRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        replyService.addReply(commentId, request, userDetails);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reply/edit/{replyId}")
    public ResponseEntity updateReply(
            @RequestBody @Valid ReplyUpdateRequestDto request,
            @PathVariable Long replyId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        replyService.updateReply(replyId, request, userDetails);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reply/delete/{replyId}")
    public ResponseEntity deleteReply(
            @PathVariable Long replyId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        replyService.deleteReply(replyId, userDetails);
        return ResponseEntity.ok().build();
    }
}
