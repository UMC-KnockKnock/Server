package com.example.knockknock.domain.repository;

import com.example.knockknock.domain.model.Like;
import com.example.knockknock.domain.model.Post;
import com.example.knockknock.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndPost(Member member, Post post);
}
