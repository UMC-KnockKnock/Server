package com.example.knockknock.domain.board.dto;

import com.example.knockknock.domain.board.entity.SearchType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class PostSearchRequestDto {
    @NotBlank
    private SearchType searchType;
    @NotBlank
    private String keyword;

    public SearchType getSearchType() {
        return SearchType.valueOf(String.valueOf(searchType));
    }
}
