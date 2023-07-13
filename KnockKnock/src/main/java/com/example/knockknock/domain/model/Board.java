package com.example.knockknock.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "board")
    private List<Post> posts = new ArrayList<>();

    @Column(name = "BOARD_NAME")
    private String name;

    public void addPost(Post post) {
        this.posts.add(post);
    }
}
