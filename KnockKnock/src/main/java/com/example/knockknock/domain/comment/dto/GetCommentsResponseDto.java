package com.example.knockknock.domain.comment.dto;
import com.example.knockknock.domain.comment.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentsResponseDto {
    private Long commentId;
    private Long postId;

    private String nickName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static GetCommentsResponseDto from(Comment comment) {
        String nickName;
        if(comment.getIsAnonymous()){
            nickName = "익명";
        } else {
            nickName = comment.getMember().getNickName();
        }
        return GetCommentsResponseDto.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getId())
                .nickName(nickName)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
