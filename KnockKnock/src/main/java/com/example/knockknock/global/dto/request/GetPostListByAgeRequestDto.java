package com.example.knockknock.global.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostListByAgeRequestDto {
    private Long boardId;
    @NotNull
    private Integer ageGroup;

}