package com.example.knockknock.domain.post.repository;

import com.example.knockknock.domain.board.entity.BoardType;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoardType(BoardType boardType);

    List<Post> findByBoardTypeAndMemberAgeBetween(BoardType boardType, int ageGroupStart, int ageGroupEnd);

    List<Post> findByBoardTypeAndTitleContainingIgnoreCase(BoardType boardType, String keyword);

    List<Post> findByBoardTypeAndContentContainingIgnoreCase(BoardType boardType, String keyword);

    List<Post> findByBoardTypeAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(BoardType boardType, String keyword, String keyword1);

    List<Post> findByMember(Member member);

    List<Post> findByBoardTypeAndHashtags_TagNameContainingIgnoreCase(BoardType boardType, String tagName);
}
