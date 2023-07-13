package com.example.knockknock.domain.controller;

import com.example.knockknock.domain.model.Board;
import com.example.knockknock.domain.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
