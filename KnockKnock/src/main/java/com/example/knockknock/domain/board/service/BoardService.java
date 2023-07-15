package com.example.knockknock.domain.board.service;

import com.example.knockknock.domain.board.dto.GetBoardsResponseDto;
import com.example.knockknock.domain.board.dto.PostSearchRequestDto;
import com.example.knockknock.domain.board.dto.PostSearchResponseDto;
import com.example.knockknock.domain.board.entity.Board;
import com.example.knockknock.domain.board.entity.SearchType;
import com.example.knockknock.domain.board.repository.BoardRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;


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
    public List<PostSearchResponseDto> searchPost(PostSearchRequestDto request) {
        String keyword = request.getKeyword();
        SearchType searchType = request.getSearchType();
        List<Post> posts = switch (searchType) {
            case TITLE -> postRepository.findByTitleContainingIgnoreCase(keyword);
            case CONTENT -> postRepository.findByContentContainingIgnoreCase(keyword);
            case TITLE_AND_CONTENT -> postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
        };

        return posts.stream()
                .map(PostSearchResponseDto::from)
                .collect(Collectors.toList());
    }
}
