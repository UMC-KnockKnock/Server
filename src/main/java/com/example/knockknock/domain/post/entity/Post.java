package com.example.knockknock.domain.post.entity;

import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.board.entity.BoardType;
import com.example.knockknock.domain.hashtag.entity.Hashtag;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.post.dto.request.PostUpdateRequestDto;
import com.example.knockknock.domain.postimage.PostImage;
import com.example.knockknock.domain.postlike.entity.PostLike;
import com.example.knockknock.domain.report.entity.Report;
import com.example.knockknock.global.timestamp.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated
    @Column(name = "BOARD_TYPE")
    private BoardType boardType;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Hashtag> hashtags = new ArrayList<>();
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "targetPost")
    private List<Report> reports = new ArrayList<>();

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();

    @Column(name = "COMMENT_COUNT")
    private int commentCount = 0;
    @Column(name = "LIKE_COUNT")
    private int likeCount = 0;

    @Column(name = "IS_ANONYMOUS")
    private Boolean isAnonymous;
    @ElementCollection
    @CollectionTable(name = "anonymous_writers", joinColumns = @JoinColumn(name = "post_id"))
    private Map<Long, Integer> anonymousWriters = new LinkedHashMap<>();

    @Column(name = "ANONYMOUS_COUNTER")
    private int anonymousCounter = 0;

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

    public void addPostImage(String imageUrl) {
        PostImage postImage = PostImage.builder()
                .post(this)
                .postImageUrl(imageUrl)
                .build();
        postImages.add(postImage);
    }

    public void removePostImage(String imageUrl) {
        for (PostImage postImage : postImages) {
            if (postImage.getPostImageUrl().equals(imageUrl)) {
                postImages.remove(postImage);
                postImage.setPost(null);
                break;
            }
        }
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
        if (comment.getIsAnonymous()) {
            Long memberId = comment.getMember().getMemberId();
            if (!anonymousWriters.containsKey(memberId)) {
                anonymousCounter += 1;
                anonymousWriters.put(memberId, anonymousCounter);
            }
        }
        this.commentCount += 1;
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
        if (comment.getIsAnonymous()) {
            Long memberId = comment.getMember().getMemberId();
            boolean isMemberStillAnonymousInThisPost = comments.stream()
                    .anyMatch(c -> c.getIsAnonymous() && c.getMember().getMemberId().equals(memberId));
            if (!isMemberStillAnonymousInThisPost) {
                anonymousWriters.remove(memberId);
            }
        }
        this.commentCount -= 1;
    }

    public Integer getAnonymousNumberByMemberId(Long memberId) {
        return anonymousWriters.get(memberId);
    }
}
