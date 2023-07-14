package com.example.knockknock.domain.board.repository;

import com.example.knockknock.domain.board.entity.Comment;
import com.example.knockknock.domain.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
