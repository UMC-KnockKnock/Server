package com.example.knockknock.domain.board.dto;

import com.example.knockknock.domain.post.dto.response.PostDetailResponseDto;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPageDto {
    private List<PostDetailResponseDto> posts;
    private boolean hasNext;

}
