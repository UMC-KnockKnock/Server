package com.example.knockknock.domain.board.dto.request;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentRegisterRequestDto {
    @NotBlank
    private Long memberId;
    @NotBlank
    private String content;
}
