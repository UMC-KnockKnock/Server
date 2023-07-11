package com.example.knockknock.domain.repository;

import com.example.knockknock.domain.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
