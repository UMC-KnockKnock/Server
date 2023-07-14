package com.example.knockknock.domain.board.dto.response;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HashtagRegisterResponseDto {
    private int tagLength;

    public HashtagRegisterResponseDto(String tagName) {
        this.tagLength = tagName.length();
    }
}
