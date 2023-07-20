package com.example.knockknock.domain.report.repository;

import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByTargetPost(Post post);

    List<Report> findByTargetComment(Comment comment);
}
