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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Builder.Default
    @OneToMany(mappedBy = "parentComment")
    private List<Reply> replies = new ArrayList<>();


    public void updateComment(String content) {
        this.content = content;
    }

    public void setPost(Post post) {
        this.post = post;
    }


    public void addReply(Reply reply) {
        replies.add(reply);
        reply.setParentComment(this);
        if (reply.getIsAnonymous()) {
            Long memberId = reply.getMember().getMemberId();
            Integer anonymousNumber = this.getPost().getAnonymousWriters().getOrDefault(memberId, 1);
            this.getPost().getAnonymousWriters().put(memberId, anonymousNumber);
        }
    }

    public void removeReply(Reply reply) {
        replies.remove(reply);
        reply.setParentComment(null);
        if (reply.getIsAnonymous()) {
            Long memberId = reply.getMember().getMemberId();
            this.getPost().getAnonymousWriters().remove(memberId);
        }
    }
}
