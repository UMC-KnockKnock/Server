package com.example.knockknock.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequestDto {
    @NotNull
    private Long memberId;
    @NotNull
    private Long boardId;
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
