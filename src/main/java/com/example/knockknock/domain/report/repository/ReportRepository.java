package com.example.knockknock.domain.report.repository;

import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByTargetPost(Post post);

    List<Report> findByTargetComment(Comment comment);

    Optional<Report> findByReporterAndTargetPost(Member reporter, Post post);

    Optional<Report> findByReporterAndTargetComment(Member reporter, Comment comment);
}
