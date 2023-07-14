package com.example.knockknock.domain.board.entity;

import com.example.knockknock.domain.post.entity.Post;
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
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long boardId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "board")
    private List<Post> posts = new ArrayList<>();

    @Column(name = "BOARD_NAME")
    private String boardName;

    public void addPost(Post post) {
        this.posts.add(post);
    }
    public void removePost(Post post){
        this.posts.remove(post);}
}
