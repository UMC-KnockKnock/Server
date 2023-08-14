package com.example.knockknock.domain.post.dto.response;

import com.example.knockknock.domain.post.entity.Post;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CountsResponseDto {
    private int likeCount;
    private int commentCount;

    public static CountsResponseDto of(Post post){
        return CountsResponseDto.builder()
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .build();
    }
}
