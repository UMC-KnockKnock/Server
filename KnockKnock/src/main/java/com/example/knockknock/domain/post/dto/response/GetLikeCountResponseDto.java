package com.example.knockknock.domain.post.dto.response;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetLikeCountResponseDto {
    private int likeCount;
}
