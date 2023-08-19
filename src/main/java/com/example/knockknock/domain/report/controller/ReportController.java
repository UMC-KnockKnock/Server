package com.example.knockknock.domain.report.controller;

import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.report.dto.response.GetReportResponseDto;
import com.example.knockknock.domain.report.dto.request.ReportRequestDto;
import com.example.knockknock.domain.report.service.ReportService;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReportController {
    private final ReportService reportService;

    @Operation(summary = "특정 게시글 신고하기 (토큰O)", description = "신고 사유를 enum으로 설정")
    @PostMapping("/post/{postId}/report")
    public ResponseEntity reportPost(
            @PathVariable Long postId,
            @RequestBody ReportRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        reportService.reportPost(postId, request, userDetails);
        return ResponseMessage.SuccessResponse("신고가 접수되었습니다.", "");
    }

    @Operation(summary = "특정 댓글 신고하기 (토큰O)", description = "신고 사유를 enum으로 설정")
    @PostMapping("/comment/{commentId}/report")
    public ResponseEntity reportComment(
            @PathVariable Long commentId,
            @RequestBody ReportRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        reportService.reportComment(commentId, request, userDetails);
        return ResponseMessage.SuccessResponse("신고가 접수되었습니다.", "");
    }

    @Operation(summary = "특정 게시글의 신고 기록 (토큰X)", description = "신고 id, 신고자의 id, 신고사유, 생성일자")
    @GetMapping("/post/{postId}/reports")
    public ResponseEntity<List<GetReportResponseDto>> getPostReports(
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(reportService.getPostReports(postId), HttpStatus.OK);
    }

    @Operation(summary = "특정 댓글의 신고 기록 (토큰X)", description = "신고 id, 신고자의 id, 신고사유, 생성일자")
    @GetMapping("/comment/{commentId}/reports")
    public ResponseEntity<List<GetReportResponseDto>> getCommentReports(
            @PathVariable Long commentId
    ) {
        return new ResponseEntity<>(reportService.getCommentReports(commentId), HttpStatus.OK);
    }
}
