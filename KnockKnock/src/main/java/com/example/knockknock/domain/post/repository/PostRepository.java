package com.example.knockknock.domain.post.repository;

import com.example.knockknock.domain.board.dto.PostSearchResponseDto;
import com.example.knockknock.domain.board.entity.Board;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoard(Board board);

    List<Post> findByBoardAndMemberAgeBetween(Board board, int ageGroupStart, int ageGroupEnd);

    List<Post> findByBoardAndTitleContainingIgnoreCase(Board board, String keyword);

    List<Post> findByBoardAndContentContainingIgnoreCase(Board board, String keyword);

    List<Post> findByBoardAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(Board board, String keyword, String keyword1);

    List<Post> findByMember(Member member);

    List<Post> findByBoardAndHashtags_TagNameContainingIgnoreCase(Board board,String tagName);
}
