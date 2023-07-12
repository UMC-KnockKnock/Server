package com.example.knockknock.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequestDto {

    @NotBlank
    private Long boardId;
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
