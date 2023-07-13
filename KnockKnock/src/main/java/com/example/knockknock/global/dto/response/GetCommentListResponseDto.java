package com.example.knockknock.global.dto.response;
import com.example.knockknock.domain.model.Comment;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentListResponseDto {
    private Long commentId;
    private Long postId;
    private String content;

    public static GetCommentListResponseDto from(Comment comment) {
        return GetCommentListResponseDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .build();
    }
}
