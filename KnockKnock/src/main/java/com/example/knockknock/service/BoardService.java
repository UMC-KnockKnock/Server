package com.example.knockknock.service;

import com.example.knockknock.domain.model.Board;
import com.example.knockknock.domain.repository.BoardRepository;
import com.example.knockknock.domain.repository.MemberRepository;
import com.example.knockknock.domain.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
