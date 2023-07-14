package com.example.knockknock.domain.board.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentRegisterRequestDto {
    @NotNull
    private Long memberId;
    @NotBlank
    private String content;
}
