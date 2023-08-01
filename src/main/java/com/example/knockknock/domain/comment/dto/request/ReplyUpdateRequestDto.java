package com.example.knockknock.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReplyUpdateRequestDto {
    @NotBlank
    private String content;
}
