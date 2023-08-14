package com.example.knockknock.domain.board.controller;

import com.example.knockknock.domain.board.dto.PostPageDto;
import com.example.knockknock.domain.board.dto.PostSearchResponseDto;
import com.example.knockknock.domain.board.entity.BoardType;
import com.example.knockknock.domain.board.entity.SearchType;
import com.example.knockknock.domain.board.service.BoardService;
import com.example.knockknock.domain.post.dto.response.PostDetailResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    @Operation(summary = "게시판의 모든 글 (토큰X)", description = "특정 게시판에 속한 모든 게시글 불러오기")
    @GetMapping("/allPosts")
    public ResponseEntity<PostPageDto> getPostsByBoard(
            @RequestParam("boardType") BoardType boardType,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return new ResponseEntity<>(boardService.getPostsByBoard(boardType, page - 1, size),HttpStatus.OK);
    }

    @Operation(summary = "키워드로 글 찾기 (토큰X)", description = "TITLE/CONTENT/TITLE_AND_CONTENT 중 하나의 searchType이 포함되어야 함")
    @GetMapping("/search")
    public ResponseEntity<PostPageDto> getPostsByKeyword(
            @RequestParam("boardType") BoardType boardType,
            @RequestParam("searchType") SearchType searchType,
            @RequestParam("keyword") String keyword,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return new ResponseEntity<>(boardService.searchPost(boardType, searchType, keyword, page - 1, size), HttpStatus.OK);
    }

    @Operation(summary = "연령대 별 게시글 필터링 (토큰X)", description = "연령대 그룹을 10 단위로 포함해야 함")
    @GetMapping("/filter")
    public ResponseEntity<PostPageDto> getPostsByAge(
            @RequestParam ("boardType") BoardType boardType,
            @RequestParam ("ageGroup") Integer ageGroup,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return new ResponseEntity<>(boardService.getPostsByAgeGroup(boardType, ageGroup, page - 1, size), HttpStatus.OK);
    }

    @Operation(summary = "해시태그로 검색 (토큰X)", description = "특정 해시태그가 포함된 모든 게시글 불러오기")
    @GetMapping("/search/hashtag")
    public ResponseEntity<List<PostDetailResponseDto>> getPostsByHashtag(
            @RequestParam("boardType") BoardType boardType,
            @RequestParam("hashtag") String hashtag
    ){
        return new ResponseEntity<>(boardService.getPostsByHashtag(boardType, hashtag), HttpStatus.OK);
    }

    @Operation(summary = "특정 사용자가 쓴 게시글 (토큰X)", description = "특정 회원이 작성한 모든 게시글 불러오기")
    @GetMapping("/member/{memberId}/posts")
    public ResponseEntity<List<PostDetailResponseDto>> getPostsByMember(
            @PathVariable Long memberId
    ) {
        return new ResponseEntity<>(boardService.getPostsByMember(memberId), HttpStatus.OK);
    }


}
