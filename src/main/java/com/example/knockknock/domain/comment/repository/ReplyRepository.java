package com.example.knockknock.domain.comment.repository;

import com.example.knockknock.domain.comment.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
