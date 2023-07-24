package com.example.knockknock.domain.comment.dto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentRegisterRequestDto {
    @NotNull
    private Long memberId;
    @NotBlank
    private String content;
    @NotNull
    @Builder.Default
    private Boolean isAnonymous = false;
}
