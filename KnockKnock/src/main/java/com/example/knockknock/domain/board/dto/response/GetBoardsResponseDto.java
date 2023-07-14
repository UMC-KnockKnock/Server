package com.example.knockknock.domain.board.dto.response;

import com.example.knockknock.domain.board.entity.Board;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardsResponseDto {
    private Long boardId;
    private String boardName;

    public static GetBoardsResponseDto from(Board board) {
        return GetBoardsResponseDto.builder()
                .boardId(board.getBoardId())
                .boardName(board.getBoardName())
                .build();
    }
}
