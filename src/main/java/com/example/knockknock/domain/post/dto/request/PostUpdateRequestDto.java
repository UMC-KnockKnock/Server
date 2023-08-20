package com.example.knockknock.domain.post.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequestDto {

    @NotNull
    private String title;
    @NotNull
    private String content;

}
