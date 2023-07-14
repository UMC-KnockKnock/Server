package com.example.knockknock.domain.board.dto.response;
import com.example.knockknock.domain.board.entity.Comment;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentsResponseDto {
    private Long commentId;
    private Long postId;
    private String content;

    public static GetCommentsResponseDto from(Comment comment) {
        return GetCommentsResponseDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .build();
    }
}
