package com.example.knockknock.dto;
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
}
