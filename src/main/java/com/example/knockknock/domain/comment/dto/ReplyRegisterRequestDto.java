package com.example.knockknock.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRegisterRequestDto {
    @NotNull
    private Long memberId;
    @NotBlank
    private String content;
    @NotNull
    @Builder.Default
    private Boolean isAnonymous = false;
}
