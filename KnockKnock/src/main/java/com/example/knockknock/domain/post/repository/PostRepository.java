package com.example.knockknock.domain.post.repository;

import com.example.knockknock.domain.board.dto.PostSearchResponseDto;
import com.example.knockknock.domain.board.entity.Board;
import com.example.knockknock.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoard(Board board);

    List<Post> findByBoardAndMemberAgeBetween(Board board, int ageGroupStart, int ageGroupEnd);

    List<Post> findByHashtags_TagName(String tagName);

    List<Post> findByTitleContainingIgnoreCase(String keyword);

    List<Post> findByContentContainingIgnoreCase(String keyword);

    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String keyword, String keyword1);
}
