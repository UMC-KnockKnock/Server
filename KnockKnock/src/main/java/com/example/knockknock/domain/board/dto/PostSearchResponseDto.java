package com.example.knockknock.domain.board.dto;

import com.example.knockknock.domain.post.entity.Post;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSearchResponseDto {
    private Long postId;
    private String nickName;
    private String title;
    private String content;
    private Integer commentCount;
    private Integer likeCount;

    public static PostSearchResponseDto from (Post post) {
        String nickName;
        if (post.getIsAnonymous()) {
            nickName = "익명";
        } else {
            nickName = post.getMember().getNickName();
        }
        return PostSearchResponseDto.builder()
                .postId(post.getPostId())
                .nickName(nickName)
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .commentCount(post.getComments().size())
                .build();
    }
}
