package com.example.knockknock.domain.comment.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentRegisterRequestDto {
    @NotBlank
    private String content;
    @NotNull
    @Builder.Default
    private Boolean isAnonymous = false;
}
