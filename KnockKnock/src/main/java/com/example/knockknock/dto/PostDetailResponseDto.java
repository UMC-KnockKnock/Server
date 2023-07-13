package com.example.knockknock.dto;
import lombok.*;
import com.example.knockknock.domain.model.Post;
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

    private int commentCount;

    public static PostDetailResponseDto of(Post post) {

        return PostDetailResponseDto.builder()
                .postId(post.getId())
                .nickName(post.getMember().getNickName())
                .title(post.getTitle())
                .content(post.getContent())
                .commentCount(post.getComments().size())
                .build();
    }
}
