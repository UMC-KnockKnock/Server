package com.example.knockknock.domain.comment.dto.response;

import com.example.knockknock.domain.comment.entity.Reply;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetRepliesResponseDto {

    private Long replyId;
    private Long parentCommentId;

    private String nickName;
    private String profileImageUrl;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public static GetRepliesResponseDto from(Reply reply){
        String nickName;

        Integer anonymousNumber = reply.getPost().getAnonymousNumberByMemberId(reply.getMember().getMemberId());
        if (reply.getIsAnonymous()) {
            nickName = "익명" + anonymousNumber;
        } else {
            nickName = reply.getMember().getNickName();
        }

        return GetRepliesResponseDto.builder()
                .replyId(reply.getReplyId())
                .parentCommentId(reply.getReplyId())
                .nickName(nickName)
                .profileImageUrl(reply.getMember().getProfileImageURL())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .modifiedAt(reply.getModifiedAt())
                .build();
    }

}

