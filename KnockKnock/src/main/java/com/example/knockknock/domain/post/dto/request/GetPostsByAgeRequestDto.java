package com.example.knockknock.domain.post.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostsByAgeRequestDto {
    private Long boardId;
    @NotNull
    private Integer ageGroup;

}