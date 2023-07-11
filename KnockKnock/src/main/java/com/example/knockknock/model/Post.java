package com.example.knockknock.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "PostHashtag",
            joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "HASHTAG_ID")
    )
    private List<HashTag> hashTags = new ArrayList<>();

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "LIKE_COUNT")
    private int likeCount;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    public void addLike() {
        this.likeCount += 1;
    }

    public void removeLike() {
        this.likeCount -= 1;
    }

    public void addHashtag(HashTag hashTag){
        this.hashTags.add(hashTag);
    }

    public void deleteHashtag(HashTag hashTag) {
        this.hashTags.remove(hashTag);
    }

}
