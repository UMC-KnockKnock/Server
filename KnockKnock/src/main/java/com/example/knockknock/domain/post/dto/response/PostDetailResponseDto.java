package com.example.knockknock.domain.post.dto.response;
import com.example.knockknock.domain.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailResponseDto {
    private Long postId;

    private String nickName;

    private String title;

    private String content;

    private int likeCount;

    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static PostDetailResponseDto of(Post post) {
        String nickName;
        if (post.getIsAnonymous()) {
            nickName = "익명";
        } else {
            nickName = post.getMember().getNickName();
        }

        return PostDetailResponseDto.builder()
                .postId(post.getId())
                .nickName(nickName)
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .commentCount(post.getComments().size())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
