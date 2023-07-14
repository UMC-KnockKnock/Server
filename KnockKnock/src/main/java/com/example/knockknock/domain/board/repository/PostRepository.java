package com.example.knockknock.domain.board.repository;

import com.example.knockknock.domain.board.entity.Board;
import com.example.knockknock.domain.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoard(Board board);

    List<Post> findByBoardAndMemberAgeBetween(Board board, int ageGroupStart, int ageGroupEnd);
}
