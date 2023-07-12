package com.example.knockknock.domain.repository;

import com.example.knockknock.domain.model.Like;
import com.example.knockknock.domain.model.Post;
import com.example.knockknock.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}
