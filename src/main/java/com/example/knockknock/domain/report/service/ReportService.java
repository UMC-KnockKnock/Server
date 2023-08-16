package com.example.knockknock.domain.report.service;


import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.comment.repository.CommentRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.member.service.MemberIsLoginService;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.domain.report.dto.response.GetReportResponseDto;
import com.example.knockknock.domain.report.dto.request.ReportRequestDto;
import com.example.knockknock.domain.report.entity.Report;
import com.example.knockknock.domain.report.repository.ReportRepository;
import com.example.knockknock.global.exception.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final MemberIsLoginService memberIsLoginService;

    @Transactional
    public void reportPost(Long postId, ReportRequestDto request, UserDetailsImpl userDetails) {
        Member reporter = memberIsLoginService.isLogin(userDetails);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        Optional<Report> reportOptional = reportRepository.findByReporterAndTargetPost(reporter, post);
        if (reportOptional.isPresent()) {
            throw new GlobalException(GlobalErrorCode.DUPLICATE_REPORT);
        } else {
            reportRepository.save(Report.builder()
                .reporter(reporter)
                .reportType(request.getReportType())
                .targetPost(post)
                .build());
            post.addReport();
        }
    }
    @Transactional
    public List<GetReportResponseDto> getPostReports(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));
        List<Report> reports = reportRepository.findByTargetPost(post);
        return reports.stream()
                .map(GetReportResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void reportComment(Long commentId, ReportRequestDto request, UserDetailsImpl userDetails) {
        Member reporter = memberIsLoginService.isLogin(userDetails);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND));
        Optional<Report> reportOptional = reportRepository.findByReporterAndTargetComment(reporter, comment);
        if (reportOptional.isPresent()) {
            throw new GlobalException(GlobalErrorCode.DUPLICATE_REPORT);
        } else {
            reportRepository.save(Report.builder()
                    .reporter(reporter)
                    .targetComment(comment)
                    .reportType(request.getReportType())
                    .build());
            comment.addReport();
        }
    }

    @Transactional
    public List<GetReportResponseDto> getCommentReports(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND));
        List<Report> reports = reportRepository.findByTargetComment(comment);
        return reports.stream()
                .map(GetReportResponseDto::from)
                .collect(Collectors.toList());
    }
}
