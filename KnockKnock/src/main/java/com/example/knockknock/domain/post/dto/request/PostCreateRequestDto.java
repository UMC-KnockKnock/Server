package com.example.knockknock.domain.post.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotNull
    private Boolean isAnonymous = false;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
