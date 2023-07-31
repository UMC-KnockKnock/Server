package com.example.knockknock.domain.comment.dto;
import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private int reportCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;


    public static GetCommentsResponseDto from(Comment comment) {
        String nickName;

        int anonymousCount = comment.getPost().getAnonymousCommentWriters().size();
        if (comment.getIsAnonymous()) {
            nickName = "익명" + anonymousCount;
        } else {
            nickName = comment.getMember().getNickName();
        }
        return GetCommentsResponseDto.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .nickName(nickName)
                .content(comment.getContent())
                .reportCount(comment.getReports().size())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

}
