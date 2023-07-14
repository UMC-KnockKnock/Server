package com.example.knockknock.domain.comment.entity;

import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.global.timestamp.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "IS_ANONYMOUS")
    private Boolean isAnonymous;

    public void updateComment(String content) {
        this.content = content;
    }
}
