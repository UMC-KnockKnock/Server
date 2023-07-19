package com.example.knockknock.domain.report.entity;

import com.example.knockknock.domain.board.entity.BoardType;
import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.global.timestamp.TimeStamped;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Report extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member reporter;

    @Enumerated(EnumType.STRING)
    @Column(name = "REPORT_TYPE")
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post targetPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment targetComment;

    @Column(name = "REPORT_CONTENT")
    private String reportContent;

    @Column(name = "IS_REPORTED")
    private boolean isReported;

    public void reportPost() {
        this.isReported = true;    }

}
