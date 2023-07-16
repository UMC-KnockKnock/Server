package com.example.knockknock.domain.board.service;

import com.example.knockknock.domain.board.dto.GetBoardsResponseDto;
import com.example.knockknock.domain.board.dto.PostSearchResponseDto;
import com.example.knockknock.domain.board.entity.Board;
import com.example.knockknock.domain.board.entity.SearchType;
import com.example.knockknock.domain.board.repository.BoardRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.dto.response.PostDetailResponseDto;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.global.exception.NotFoundBoardException;
import com.example.knockknock.global.exception.NotFoundMemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public void createBoard(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public List<GetBoardsResponseDto> getAllBoards(){
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(GetBoardsResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostDetailResponseDto> getPostsByBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException("게시판을 찾을 수 없습니다."));
        List<Post> posts = postRepository.findByBoard(board);
        return posts.stream()
                .map(PostDetailResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostDetailResponseDto> getPostsByAgeGroup(Long boardId, Integer ageGroup) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException("게시판을 찾을 수 없습니다."));
        int ageGroupStart = ageGroup;
        int ageGroupEnd = ageGroupStart + 9;
        List<Post> posts = postRepository.findByBoardAndMemberAgeBetween(board, ageGroupStart, ageGroupEnd);
        return posts.stream()
                .map(PostDetailResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostDetailResponseDto> getPostsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));
        List<Post> posts = postRepository.findByMember(member);
        return posts.stream()
                .map(PostDetailResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostDetailResponseDto> getPostsByHashtag(Long boardId, String tagName) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException("게시판을 찾을 수 없습니다."));

        List<Post> posts = postRepository.findByBoardAndHashtags_TagNameContainingIgnoreCase(board, tagName);
        return posts.stream()
                .map(PostDetailResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostSearchResponseDto> searchPost(Long boardId, SearchType searchType, String keyword) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException("게시판을 찾을 수 없습니다."));

        List<Post> posts = switch (searchType) {
            case TITLE -> postRepository.findByBoardAndTitleContainingIgnoreCase(board, keyword);
            case CONTENT -> postRepository.findByBoardAndContentContainingIgnoreCase(board, keyword);
            case TITLE_AND_CONTENT -> postRepository.findByBoardAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(board, keyword, keyword);
        };

        return posts.stream()
                .map(PostSearchResponseDto::from)
                .collect(Collectors.toList());
    }
}
