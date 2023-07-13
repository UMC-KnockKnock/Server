package com.example.knockknock.global.dto.response;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetLikeCountResponseDto {
    private int likeCount;
}
