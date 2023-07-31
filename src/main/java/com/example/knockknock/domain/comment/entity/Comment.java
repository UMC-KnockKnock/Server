package com.example.knockknock.domain.comment.entity;

import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.report.entity.Report;
import com.example.knockknock.global.timestamp.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "targetComment")
    private List<Report> reports = new ArrayList<>();

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "IS_ANONYMOUS")
    private Boolean isAnonymous;

    public void updateComment(String content) {
        this.content = content;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
