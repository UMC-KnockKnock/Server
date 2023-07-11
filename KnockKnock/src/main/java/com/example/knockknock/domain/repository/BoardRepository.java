package com.example.knockknock.domain.repository;

import com.example.knockknock.domain.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
