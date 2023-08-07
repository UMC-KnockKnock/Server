package com.example.knockknock.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRegisterRequestDto {
    @NotBlank
    private String content;
    @NotNull
    @Builder.Default
    private Boolean isAnonymous = false;
}
