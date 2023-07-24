package com.example.knockknock.domain.postlike.dto;

import com.example.knockknock.domain.postlike.entity.PostLike;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeDetailResponseDto {
    private Long postLikeId;
    private Long postId;
    private Long memberId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public static PostLikeDetailResponseDto from(PostLike postLike) {
        if (postLike.isLiked()) {
            return PostLikeDetailResponseDto.builder()
                    .postLikeId(postLike.getPostLikeId())
                    .postId(postLike.getPost().getPostId())
                    .memberId(postLike.getMember().getMemberId())
                    .createdAt(postLike.getCreatedAt())
                    .build();
        } else {
            return null;
        }
    }
}
