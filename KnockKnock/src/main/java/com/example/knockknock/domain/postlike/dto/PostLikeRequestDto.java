package com.example.knockknock.domain.postlike.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeRequestDto {
    @NotNull
    private Long memberId;
    @NotNull
    private Long postId;
}
