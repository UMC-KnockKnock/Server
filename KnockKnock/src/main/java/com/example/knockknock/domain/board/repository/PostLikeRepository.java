package com.example.knockknock.domain.board.repository;

import com.example.knockknock.domain.board.entity.PostLike;
import com.example.knockknock.domain.board.entity.Post;
import com.example.knockknock.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByMemberAndPost(Member member, Post post);
}
