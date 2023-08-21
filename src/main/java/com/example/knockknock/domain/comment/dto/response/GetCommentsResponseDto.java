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
        String profileImageUrl;
        Integer anonymousNumber = comment.getPost().getAnonymousNumberByMemberId(comment.getMember().getMemberId());
        if (comment.getIsAnonymous()) {
            nickName = "익명" + anonymousNumber;
            //익명일 때 기본 프로필 사진
            profileImageUrl = "https://knockknockimage.s3.ap-northeast-2.amazonaws.com/%EA%B8%B0%EB%B3%B8+%EC%9D%B4%EB%AF%B8%EC%A7%80.png";
        } else {
            nickName = comment.getMember().getNickName();
            profileImageUrl = comment.getMember().getProfileImageURL();
        }


        return GetCommentsResponseDto.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .nickName(nickName)
                .profileImageUrl(profileImageUrl)
                .content(comment.getContent())
                .reportCount(comment.getReportCount())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

}
