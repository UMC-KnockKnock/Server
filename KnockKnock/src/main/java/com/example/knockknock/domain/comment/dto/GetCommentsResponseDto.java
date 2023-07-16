package com.example.knockknock.domain.comment.dto;
import com.example.knockknock.domain.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
