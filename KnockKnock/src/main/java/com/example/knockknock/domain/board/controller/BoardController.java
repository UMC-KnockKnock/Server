package com.example.knockknock.domain.board.controller;

import com.example.knockknock.domain.board.dto.response.GetBoardsResponseDto;
import com.example.knockknock.domain.board.entity.Board;
import com.example.knockknock.domain.board.service.BoardService;
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
}
