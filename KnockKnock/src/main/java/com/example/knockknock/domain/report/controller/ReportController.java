package com.example.knockknock.domain.report.controller;

import com.example.knockknock.domain.report.dto.ReportRequestDto;
import com.example.knockknock.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/report")
@RequiredArgsConstructor
@RestController
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/post/{postId}")
    public ResponseEntity reportPost(
            @PathVariable Long postId,
            @RequestBody ReportRequestDto request
    ) {
        reportService.reportPost(postId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity reportComment(
            @PathVariable Long commentId,
            @RequestBody ReportRequestDto request
    ) {
        reportService.reportComment(commentId, request);
        return ResponseEntity.ok().build();
    }


}
