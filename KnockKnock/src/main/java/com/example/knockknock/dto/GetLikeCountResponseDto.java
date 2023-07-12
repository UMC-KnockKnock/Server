package com.example.knockknock.dto;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetLikeCountResponseDto {
    private int likeCount;
}
