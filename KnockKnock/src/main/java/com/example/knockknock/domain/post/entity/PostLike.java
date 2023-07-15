package com.example.knockknock.domain.post.entity;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.post.entity.Post;
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
public class PostLike extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSTLIKE_ID")
    private Long postLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(name = "IS_LIKED")
    private boolean isLiked;

    public void likePost() {
        this.isLiked = true;
    }

    public void deletePostLike() {
        this.isLiked = false;
    }
}
