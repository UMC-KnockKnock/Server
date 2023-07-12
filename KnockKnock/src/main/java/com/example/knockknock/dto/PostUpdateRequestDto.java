package com.example.knockknock.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
