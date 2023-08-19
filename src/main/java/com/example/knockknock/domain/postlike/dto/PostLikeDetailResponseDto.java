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
    private Long postId;
    private Long memberId;
    private String nickName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public static PostLikeDetailResponseDto from(PostLike postLike) {
            return PostLikeDetailResponseDto.builder()
                    .postId(postLike.getPost().getPostId())
                    .memberId(postLike.getMember().getMemberId())
                    .nickName(postLike.getMember().getNickName())
                    .createdAt(postLike.getCreatedAt())
                    .build();
        }
    }

