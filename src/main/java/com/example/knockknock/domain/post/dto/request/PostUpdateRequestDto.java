package com.example.knockknock.domain.post.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

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

    private List<String> imagesToDelete;

}
