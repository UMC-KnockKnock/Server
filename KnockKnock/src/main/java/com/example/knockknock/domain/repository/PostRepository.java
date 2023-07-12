package com.example.knockknock.domain.repository;

import com.example.knockknock.domain.model.Board;
import com.example.knockknock.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoard(Board board);
}
