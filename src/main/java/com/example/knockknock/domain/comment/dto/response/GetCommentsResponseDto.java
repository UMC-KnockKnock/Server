package com.example.knockknock.domain.comment.dto.response;
import com.example.knockknock.domain.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentsResponseDto {
    private Long commentId;
    private Long postId;

    private String nickName;
    private String profileImageUrl;
    private String content;
    private int reportCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;



    public static GetCommentsResponseDto from(Comment comment) {
        String nickName;

        Integer anonymousNumber = comment.getPost().getAnonymousNumberByMemberId(comment.getMember().getMemberId());
        if (comment.getIsAnonymous()) {
            nickName = "익명" + anonymousNumber;
        } else {
            nickName = comment.getMember().getNickName();
        }


        return GetCommentsResponseDto.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .nickName(nickName)
                .profileImageUrl(comment.getMember().getProfileImageURL())
                .content(comment.getContent())
                .reportCount(comment.getReports().size())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

}
