package com.example.knockknock.domain.board.dto.request;
import jakarta.transaction.Transactional;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateRequestDto {
    @NotBlank
    private String content;
}
