package com.example.knockknock.domain.board.controller;

import com.example.knockknock.domain.board.dto.GetBoardsResponseDto;
import com.example.knockknock.domain.board.dto.PostSearchResponseDto;
import com.example.knockknock.domain.board.entity.Board;
import com.example.knockknock.domain.board.entity.SearchType;
import com.example.knockknock.domain.board.service.BoardService;
import com.example.knockknock.domain.post.dto.response.PostDetailResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity createBoard(
            @RequestBody Board board
    ) {
        boardService.createBoard(board);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<GetBoardsResponseDto>> getAllBoards(){
        return new ResponseEntity<>(boardService.getAllBoards(), HttpStatus.OK);
    }
    @GetMapping("/{boardId}/allPosts")
    public ResponseEntity<List<PostDetailResponseDto>> getPostsByBoard(
            @PathVariable Long boardId) {
        return new ResponseEntity<>(boardService.getPostsByBoard(boardId), HttpStatus.OK);
    }

    @GetMapping("{boardId}/search")
    public ResponseEntity<List<PostSearchResponseDto>> getPostsByKeyword(
            @PathVariable Long boardId,
            @RequestParam("searchType") SearchType searchType,
            @RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(boardService.searchPost(boardId, searchType, keyword), HttpStatus.OK);
    }
    @GetMapping("/{boardId}/search/hashtag")
    public ResponseEntity<List<PostDetailResponseDto>> getPostsByHashtag(
            @PathVariable Long boardId,
            @RequestParam("hashtag") String hashtag
    ){
        return new ResponseEntity<>(boardService.getPostsByHashtag(boardId, hashtag), HttpStatus.OK);
    }

    @GetMapping("/{memberId}/posts")
    public ResponseEntity<List<PostDetailResponseDto>> getPostsByMember(
            @PathVariable Long memberId
    ) {
        return new ResponseEntity<>(boardService.getPostsByMember(memberId), HttpStatus.OK);
    }
    @GetMapping("/{boardId}/filter/ageGroup")
    public ResponseEntity<List<PostDetailResponseDto>> getPostsByAge(
            @PathVariable Long boardId,
            @RequestParam Integer ageGroup
    ) {
        return new ResponseEntity<>(boardService.getPostsByAgeGroup(boardId, ageGroup), HttpStatus.OK);
    }

}
