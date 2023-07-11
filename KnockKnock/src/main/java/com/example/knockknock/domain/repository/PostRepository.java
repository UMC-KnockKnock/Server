package com.example.knockknock.domain.repository;

import com.example.knockknock.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
