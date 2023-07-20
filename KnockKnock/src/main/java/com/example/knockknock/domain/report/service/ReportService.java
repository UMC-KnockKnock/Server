package com.example.knockknock.domain.report.service;


import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.comment.repository.CommentRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.domain.report.dto.ReportRequestDto;
import com.example.knockknock.domain.report.entity.Report;
import com.example.knockknock.domain.report.repository.ReportRepository;
import com.example.knockknock.global.exception.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private ReportRepository reportRepository;
    private CommentRepository commentRepository;
    private MemberRepository memberRepository;
    private PostRepository postRepository;

    public ReportService(ReportRepository reportRepository, CommentRepository commentRepository, MemberRepository memberRepository, PostRepository postRepository) {
        this.reportRepository = reportRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public void reportPost(Long postId, ReportRequestDto request) {
        Member reporter = memberRepository.findById(request.getReporterId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        reportRepository.save(Report.builder()
                .reporter(reporter)
                .targetPost(post)
                .reportType(request.getReportType())
                .reportContent(request.getReportContent())
                .build());
    }

    @Transactional
    public void reportComment(Long commentId, ReportRequestDto request) {
        Member reporter = memberRepository.findById(request.getReporterId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND));

        reportRepository.save(Report.builder()
                .reporter(reporter)
                .targetComment(comment)
                .reportType(request.getReportType())
                .reportContent(request.getReportContent())
                .build());
    }
}
