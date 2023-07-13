package com.example.knockknock.global.dto.request;
import jakarta.transaction.Transactional;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateRequestDto {
    @NotBlank
    private String content;
}
