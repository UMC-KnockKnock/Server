package com.example.knockknock.domain.repository;

import com.example.knockknock.domain.model.Comment;
import com.example.knockknock.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
