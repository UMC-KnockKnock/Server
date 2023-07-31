package com.example.knockknock.domain.comment.dto.response;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentRegisterResponseDto {
    private int commentLength;

    public CommentRegisterResponseDto(String content) {
        this.commentLength = content.length();
    }
}
