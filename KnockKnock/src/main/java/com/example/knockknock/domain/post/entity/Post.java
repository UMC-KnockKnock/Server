package com.example.knockknock.domain.post.entity;

import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.board.entity.BoardType;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.post.dto.request.PostUpdateRequestDto;
import com.example.knockknock.global.timestamp.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "BOARD_TYPE")
    private BoardType boardType;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Hashtag> hashtags = new ArrayList<>();

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "LIKE_COUNT")
    private int likeCount;

    @Column(name = "IS_ANONYMOUS")
    private Boolean isAnonymous;

    public void addLike() {
        this.likeCount += 1;
    }

    public void removeLike() {
        this.likeCount -= 1;
    }


    public void updatePost(PostUpdateRequestDto request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
