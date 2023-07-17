package com.example.knockknock.domain.board.controller;

import com.example.knockknock.domain.board.dto.PostSearchResponseDto;
import com.example.knockknock.domain.board.entity.BoardType;
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


    @GetMapping("/allPosts")
    public ResponseEntity<List<PostDetailResponseDto>> getPostsByBoard(
            @RequestParam("boardType") BoardType boardType) {
        return new ResponseEntity<>(boardService.getPostsByBoard(boardType), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostSearchResponseDto>> getPostsByKeyword(
            @RequestParam("boardType") BoardType boardType,
            @RequestParam("searchType") SearchType searchType,
            @RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(boardService.searchPost(boardType, searchType, keyword), HttpStatus.OK);
    }
    @GetMapping("/search/hashtag")
    public ResponseEntity<List<PostDetailResponseDto>> getPostsByHashtag(
            @RequestParam("boardType") BoardType boardType,
            @RequestParam("hashtag") String hashtag
    ){
        return new ResponseEntity<>(boardService.getPostsByHashtag(boardType, hashtag), HttpStatus.OK);
    }

    @GetMapping("/{memberId}/posts")
    public ResponseEntity<List<PostDetailResponseDto>> getPostsByMember(
            @PathVariable Long memberId
    ) {
        return new ResponseEntity<>(boardService.getPostsByMember(memberId), HttpStatus.OK);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<PostDetailResponseDto>> getPostsByAge(
            @RequestParam ("boardType") BoardType boardType,
            @RequestParam ("ageGroup") Integer ageGroup
    ) {
        return new ResponseEntity<>(boardService.getPostsByAgeGroup(boardType, ageGroup), HttpStatus.OK);
    }

}
